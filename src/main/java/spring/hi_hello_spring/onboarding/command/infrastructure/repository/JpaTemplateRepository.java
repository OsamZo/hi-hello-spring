package spring.hi_hello_spring.onboarding.command.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.Template;
import spring.hi_hello_spring.onboarding.command.domain.repository.TemplateRepository;

public interface JpaTemplateRepository extends TemplateRepository, JpaRepository<Template, Long> {
}
