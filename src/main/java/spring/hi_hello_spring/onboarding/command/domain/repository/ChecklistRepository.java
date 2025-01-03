package spring.hi_hello_spring.onboarding.command.domain.repository;

import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.Checklist;

import java.util.List;

public interface ChecklistRepository {

    List<Checklist> findByTemplateSeq(Long templateSeq);
}
