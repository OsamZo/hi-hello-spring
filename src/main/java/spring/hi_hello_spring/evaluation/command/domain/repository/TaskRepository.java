package spring.hi_hello_spring.evaluation.command.domain.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.Task;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.TaskSubmit;

import java.util.List;

public interface TaskRepository {
    Task save(Task task);

    TaskSubmit save(TaskSubmit taskSubmit);

    boolean existsById(Long taskSeq);

    void deleteById(Long taskSeq);

    @Query("SELECT t.taskSeq FROM Task t WHERE t.templateSeq = :templateSeq")
    List<Long> findTaskSeqsByTemplateSeq(Long templateSeq);

    List<Task> findByTemplateSeq(Long templateSeq);
}


