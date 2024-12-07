package spring.hi_hello_spring.evaluation.command.domain.repository;

import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.TaskEval;

public interface TaskEvalRepository {
    
    TaskEval save(TaskEval taskEval);
    boolean existsByTaskSubmitSeqAndEvalListSeq(Long taskSubmitSeq, Long evalListSeq);
}