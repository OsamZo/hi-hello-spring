package spring.hi_hello_spring.wiki.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.common.util.WikiUtil;
import spring.hi_hello_spring.wiki.query.dto.*;
import spring.hi_hello_spring.wiki.query.mapper.WikiMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WikiQueryService {

    private final WikiMapper wikiMapper;
    private final WikiUtil wikiUtil;

    // 위키 목록 조회하기
    public List<WikiListQueryDTO> getAllWikis() {
        return wikiMapper.findAllWikis();
    }

    // 위키 히스토리 목록 조회하기
    public List<WikiHistoryListQueryDTO> getWikiHistories(Long wikiSeq) { return wikiMapper.findWikiHistories(wikiSeq);}

    // 위키 조회하기
    public WikiQueryDTO getWikiByWikiSeq(Long wikiSeq) {
        WikiSnapshotQueryDTO wikiSnapshotQueryDTO = getWikiSnapshotByWikiSeq(wikiSeq);
        List<WikiModContentQueryDTO> wikiModContentQueryDTOS = getWikiModContents(wikiSeq, wikiSnapshotQueryDTO.getWikiSnapshotSeq());
        return getWikiQueryDTO(wikiSeq, wikiSnapshotQueryDTO, wikiModContentQueryDTOS);
    }

    private String convertWikiContentToHtml(HashMap<Integer, String> wikiContentHashMap) {
        return wikiContentHashMap
                .entrySet()
                .stream().sorted(Map.Entry.comparingByKey())  // Key로 정렬
                .map(Map.Entry::getValue)  // value만 추출
                .collect(Collectors.joining());  // value만 합치기
    }

    // 위키 스냅샷 조회하기
    public WikiSnapshotQueryDTO getWikiSnapshotByWikiSeq(Long wikiSeq) {
        return wikiMapper.findWikiSnapshotByWikiSeq(wikiSeq);
    }

    // 위키 수정 내용 리스트 조회하기 (최신 스냅샷 이후~현재까지의 수정 내역)
    public List<WikiModContentQueryDTO> getWikiModContents(Long wikiSeq, Long wikiSnapshotSeq) {
        return wikiMapper.findWikiModContentsByWikiSeqAndWikiSnapshotSeq(wikiSeq, wikiSnapshotSeq);
    }

    // 위키의 최종 수정시간 조회하기
    public LocalDateTime getWikiLatestModDate(Long wikiSeq) {
        return wikiMapper.findWikiLatestModDateByWikiSeq(wikiSeq);
    }

    /*복원 관련 위키 조회*/
    // 특정 버전 기준 최신 위키 스냅샷 조회하기
    public WikiSnapshotQueryDTO getLatestWikiSnapshot(Long wikiSeq, Long wikiModContentSeq) {
        return wikiMapper.findLatestWikiSnapshot(wikiSeq, wikiModContentSeq);
    }

    // 현재 조회하려는 버전 이전의 수정 리스트 조회하기
    public List<WikiModContentQueryDTO> getWikiModContentsLessThanWikiModContentSeq(Long wikiSeq, Long wikiSnapshotSeq, Long wikiModContentSeq) {
        return wikiMapper.findWikiModContentsLessThanWikiModContentSeq(wikiSeq, wikiSnapshotSeq,wikiModContentSeq);
    }

    // 특정 버전의 위키 조회하기
    public WikiQueryDTO getWikiByWikiSeqAndWikiModContentSeq(Long wikiSeq, Long wikiModContentSeq) {
        WikiSnapshotQueryDTO wikiSnapshotQueryDTO = getLatestWikiSnapshot(wikiSeq, wikiModContentSeq);
        List<WikiModContentQueryDTO> wikiModContentQueryDTOS = getWikiModContentsLessThanWikiModContentSeq(wikiSeq, wikiSnapshotQueryDTO.getWikiSnapshotSeq(), wikiModContentSeq);
        return getWikiQueryDTO(wikiSeq, wikiSnapshotQueryDTO, wikiModContentQueryDTOS);
    }

    // 위키 조회 로직
    private WikiQueryDTO getWikiQueryDTO(Long wikiSeq, WikiSnapshotQueryDTO wikiSnapshotQueryDTO, List<WikiModContentQueryDTO> wikiModContentQueryDTOS) {
        List<String> wikiModContents = new ArrayList<>();
        for (WikiModContentQueryDTO wikiModContentQueryDTO : wikiModContentQueryDTOS) {
            wikiModContents.add(wikiModContentQueryDTO.getWikiModContent());
        }
        HashMap<Integer, String> wikiContentHashMap = wikiUtil.mergeWikiContent(wikiSnapshotQueryDTO.getWikiSnapshotContent(), wikiModContents);
        String wikiContent = convertWikiContentToHtml(wikiContentHashMap);
        WikiQueryDTO wikiQueryDTO = new WikiQueryDTO();
        wikiQueryDTO.setWikiTitle(wikiSnapshotQueryDTO.getWikiTitle());
        wikiQueryDTO.setWikiContent(wikiContent);
        wikiQueryDTO.setModDate(getWikiLatestModDate(wikiSeq));

        return wikiQueryDTO;
    }
}