package spring.hi_hello_spring.evaluation.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TaskMentorDetailQueryDTO {

    private Long taskSeq;
    private String taskType;
    private String taskTitle;
    private String taskUrl;
    private Long templateSeq;
    private String taskContent;
    private String templateTaskRound;
    private LocalDateTime templateEndAt;
    private List<EvalListQueryDTO> evalList;
}
