package spring.hi_hello_spring.onboarding.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.onboarding.query.mapper.ChecklistMapper;

@Service
@RequiredArgsConstructor
public class ChecklistQueryService {

    private final ChecklistMapper checklistMapper;

    public boolean getChecklistStatusByTemplateSeq(Long templateSeq, Long employeeSeq) {
        int uncheckedListQty = checklistMapper.findUncheckedListByTemplateSeq(templateSeq, employeeSeq);
        return uncheckedListQty == 0;
    }
}