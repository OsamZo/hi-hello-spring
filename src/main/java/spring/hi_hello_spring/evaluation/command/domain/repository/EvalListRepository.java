package spring.hi_hello_spring.evaluation.command.domain.repository;

import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.EvalList;
import java.util.Optional;

public interface EvalListRepository {

    EvalList save(EvalList evalList);

    boolean existsById(Long evalListSeq);

    Optional<EvalList> findById(Long evalListSeq);
}