package spring.hi_hello_spring.evaluation.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TaskAllListQueryDTO {

    private Long taskSeq;
    private Long departmentSeq;
    private String departmentName;
    private Long templateSeq;
    private String templateTaskRound;
    private Long employeeSeq;
    private String displayName;
    private String taskContent;

}
