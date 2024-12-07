package spring.hi_hello_spring.evaluation.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "task_eval")
@NoArgsConstructor
@Getter
public class TaskEval extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskEvalSeq;

    private Long evalListSeq;

    private Long taskSubmitSeq;

    private int taskScore;

    public void updateTaskSubmitSeq(Long taskSubmitSeq) {
        this.taskSubmitSeq = taskSubmitSeq;
    }

    @Builder
    public TaskEval(Long evalListSeq, Long taskSubmitSeq, int taskScore) {
        this.evalListSeq = evalListSeq;
        this.taskSubmitSeq = taskSubmitSeq;
        this.taskScore = taskScore;
    }
}