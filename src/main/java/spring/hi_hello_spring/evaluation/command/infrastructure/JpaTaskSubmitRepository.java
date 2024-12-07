package spring.hi_hello_spring.evaluation.command.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.TaskSubmit;
import spring.hi_hello_spring.evaluation.command.domain.repository.TaskSubmitRepository;

public interface JpaTaskSubmitRepository extends TaskSubmitRepository, JpaRepository<TaskSubmit, Long> {
}