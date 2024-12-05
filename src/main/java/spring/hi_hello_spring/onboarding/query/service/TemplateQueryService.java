package spring.hi_hello_spring.onboarding.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.onboarding.query.dto.TemplateAllQueryDTO;
import spring.hi_hello_spring.onboarding.query.mapper.TemplateMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateQueryService {

    private final TemplateMapper templateMapper;

    /* 온보딩 스토리보드 순서 전체 조회 */
    public List<TemplateAllQueryDTO> getAllTemplate(){

        return templateMapper.findAllTemplate();
    }
}
