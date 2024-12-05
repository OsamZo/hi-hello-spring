package spring.hi_hello_spring.wiki.command.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.wiki.command.application.dto.WikiCreateRequestDTO;
import spring.hi_hello_spring.wiki.command.application.dto.WikiUpdateRequestDTO;
import spring.hi_hello_spring.wiki.command.domain.aggregate.entity.Wiki;
import spring.hi_hello_spring.wiki.command.domain.aggregate.entity.WikiModContent;
import spring.hi_hello_spring.wiki.command.domain.aggregate.entity.WikiSnapshot;
import spring.hi_hello_spring.wiki.command.domain.repository.WikiModContentRepository;
import spring.hi_hello_spring.wiki.command.domain.repository.WikiRepository;
import spring.hi_hello_spring.wiki.command.domain.repository.WikiSnapshotRepository;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WikiService {

    private final WikiRepository wikiRepository;
    private final WikiSnapshotRepository wikiSnapshotRepository;
    private final WikiModContentRepository wikiModContentRepository;

    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    @Transactional
    public void createWiki(WikiCreateRequestDTO wikiCreateRequestDTO) {

        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();

        // 위키 생성
        Wiki wiki = Wiki.builder()
                .wikiTitle(wikiCreateRequestDTO.getWikiTitle())
                .wikiCurrentVer(1)
                .build();
        wikiRepository.save(wiki);

        // 위키 snapshot 생성
        WikiSnapshot wikiSnapshot = modelMapper.map(wikiCreateRequestDTO, WikiSnapshot.class);
        wikiSnapshot.updateWikiSeq(wiki.getWikiSeq());
        wikiSnapshot.updateWikiSnapshotVer(1);
        wikiSnapshotRepository.save(wikiSnapshot);

        // 변경 내역 기록(처음 위키 등록 시에는 전체 내용을 DIFF로 저장)
        WikiModContent wikiModContent = WikiModContent.builder()
                .wikiSeq(wiki.getWikiSeq())
                .employeeSeq(1L)
                .wikiSnapshotSeq(wikiSnapshot.getWikiSnapshotSeq())
                .modContent("{\"1\": \"" + wikiCreateRequestDTO.getWikiSnapshotContent() + "\"}")
                .build();
        wikiModContentRepository.save(wikiModContent);
    }

    @Transactional
    public void updateWiki(Long wikiSeq, Long employeeSeq, WikiUpdateRequestDTO wikiUpdateRequestDTO) throws IOException {
        // 기존 위키 문서 가져오기
        Wiki wiki = wikiRepository.findById(wikiSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        // 현재 위키 문서의 버전
        int currentVersion = wiki.getWikiCurrentVer();

        // 위키 문서의 현재 버전 이전의 가장 가까운 스냅샷 버전
        int latestSnapshotVer = currentVersion / 10 + 1;

        // 위키 문서의 현재 버전 이전의 가장 가까운 스냅샷
        WikiSnapshot latestWikiSnapshot = wikiSnapshotRepository.findByWikiSeqAndWikiSnapshotVer(wikiSeq, latestSnapshotVer)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        // 최신 스냅샷의 snapshotSeq 가져오기
        Long latestWikiSnapshotSeq = latestWikiSnapshot.getWikiSnapshotSeq();

        // 최신 스냅샷을 각 문단별로 쪼개기
        String[] latestSnapshotParagraphs = latestWikiSnapshot.getWikiSnapshotContent().split("\n");

        // 최신 스냅샷을 기준으로 변경된 내용들 찾기
        List<String> modContents = wikiModContentRepository.findModContentsByWikiSeqAndWikiSnapshotSeqOrderByWikiModContentSeq(wikiSeq, latestWikiSnapshotSeq);

        // 현재 버전의 위키 전체 내용을 저장할 배열
        String[] currentWikiContent = new String[latestSnapshotParagraphs.length];

        // 수정된 내용을 저장할 리스트(JSON 형식으로 반환)
        List<Map<String, String>> modContentsList = new ArrayList<>();

        for (String modContent : modContents) {
            // json 형식을 hashmap으로 변환하기
            HashMap<Integer, Map<String, String>> modifications = parseModContent(modContent);

            // 수정 사항을 기존 문단에 반영(즉 현재 버전의 위키 전체 내용)
            currentWikiContent = applyModification(latestSnapshotParagraphs, modifications);
        }

        // 해당 위키의 해당 스냅샷 개수 가져오기
        int numOfVersions = wikiModContentRepository.countByWikiSeqAndWikiSnapshotSeq(wikiSeq, latestWikiSnapshotSeq).intValue();

        // 만약 현재 이 위키에 존재하는 버전이 10의 배수라면, 다음 버전은 snapshot에 저장되어야 한다.
        if (numOfVersions % 10 == 0) {
            String modContent = wikiUpdateRequestDTO.getWikiModContent().replace("\n", "\\n").replace("\r", "\\r");
            WikiSnapshot newWikiSnapshot = saveSnapshot(wikiSeq, numOfVersions,modContent);
            latestWikiSnapshotSeq = newWikiSnapshot.getWikiSnapshotSeq();
        }

        // 수정내용 전체를 문단 단위로 쪼개서 modifiedContent에 저장
        String[] modifiedContent = wikiUpdateRequestDTO.getWikiModContent().split("\n");
        Map<String, String> modification = new HashMap<>();
        int minParagraphQty = Math.min(currentWikiContent.length, modifiedContent.length);
        for (int i = 0; i < minParagraphQty; i++) {
            if (!Objects.equals(currentWikiContent[i], modifiedContent[i])) {
                modification.put("index", String.valueOf(i + 1));
                modification.put("original", currentWikiContent[i]);
                modification.put("modified", modifiedContent[i]);

                modContentsList.add(modification);
            }
        }

        if (currentWikiContent.length < modifiedContent.length) {
            for (int i = minParagraphQty; i < modifiedContent.length; i++) {
                modification.put("index", String.valueOf(i + 1));
                modification.put("original", "");
                modification.put("modified", modifiedContent[i]);

                modContentsList.add(modification);
            }
        }

        // modContentsList를 String(Json)으로 변환
        String modContentJson = objectMapper.writeValueAsString(modContentsList);

        // 변경 내역 기록
        WikiModContent wikiModContent = WikiModContent.builder()
                .wikiSeq(wiki.getWikiSeq())
                .employeeSeq(1L)
                .wikiSnapshotSeq(latestWikiSnapshotSeq)
                .modContent(modContentJson)
                .build();
        wikiModContentRepository.save(wikiModContent);

        // wiki 엔티티의 wikiCurrentVer 수정
        wiki.updateWikiCurrentVer(numOfVersions + 1);
    }

    // json 형태로 들어있는 modContent를 hashmap으로 파싱
    private HashMap<Integer, Map<String, String>> parseModContent(String modContentJson) {
        HashMap<Integer, Map<String, String>> modContentHashMap = new HashMap<>();

        try {
            List<Map<String, Object>> modifications = objectMapper.readValue(
                    modContentJson, new TypeReference<List<Map<String, Object>>>() {
                    }
            );

            // index를 키, original과 modified를 값으로 변환
            for (Map<String, Object> modification : modifications) {
                Integer index = (Integer) modification.get("index");
                Map<String, String> valueMap = new HashMap<>();
                valueMap.put("original", (String) modification.get("original"));
                valueMap.put("modified", (String) modification.get("modified"));

                modContentHashMap.put(index, valueMap);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return modContentHashMap;
    }

    private String[] applyModification(String[] paragraphs, HashMap<Integer, Map<String, String>> modifications) {
        for(Map.Entry<Integer, Map<String, String>> modification : modifications.entrySet()) {
            Integer index = modification.getKey();
            String modified = (String) modification.getValue().get("modified");

            paragraphs[index] = modified;
        }
        return paragraphs;
    }

    private WikiSnapshot saveSnapshot(Long wikiSeq, int numOfVersions, String wikiModContent) {
        WikiSnapshot wikiSnapshot = WikiSnapshot.builder()
                .wikiSeq(wikiSeq)
                .wikiSnapshotVer(numOfVersions / 10 + 1)
                .wikiSnapshotContent(wikiModContent)
                .build();
        wikiSnapshotRepository.save(wikiSnapshot);
        return wikiSnapshot;
    }
}