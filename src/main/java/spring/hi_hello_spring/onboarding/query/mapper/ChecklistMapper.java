package spring.hi_hello_spring.onboarding.query.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChecklistMapper {

    Integer findUncheckedListByTemplateSeq(Long templateSeq, Long employeeSeq);
}