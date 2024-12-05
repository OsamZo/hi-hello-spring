package spring.hi_hello_spring.onboarding.command.domain.repository;

import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.Template;

public interface TemplateRepository {

    Template save(Template template);

    Template findByTemplateTaskRound(String templateTaskRound);
}
