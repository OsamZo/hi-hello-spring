package spring.hi_hello_spring.evaluation.command.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.TaskEval;
import spring.hi_hello_spring.evaluation.command.domain.repository.TaskEvalRepository;

public interface JpaTaskEvalRepository extends TaskEvalRepository, JpaRepository<TaskEval, Long> {
}