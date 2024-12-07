package spring.hi_hello_spring.evaluation.command.domain.repository;

public interface TaskSubmitRepository {

    boolean existsById(Long taskSubmitSeq);
}