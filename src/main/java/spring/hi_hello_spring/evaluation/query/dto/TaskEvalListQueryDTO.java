package spring.hi_hello_spring.evaluation.query.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TaskEvalListQueryDTO {

    private Long departmentSeq;
    private String departmentName;
    private Long templateSeq;
    private String templateTaskRound;
    private Long taskSubmitSeq;
    private String taskType;
    private Long submitterSeq;
    private String submitterName;
    private Long taskSeq;
    private String taskContent;
    private Integer taskTotalScore;
}