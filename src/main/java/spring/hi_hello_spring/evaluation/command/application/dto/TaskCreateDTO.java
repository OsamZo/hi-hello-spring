package spring.hi_hello_spring.evaluation.command.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.TaskType;
import spring.hi_hello_spring.group.command.application.dto.TaskRequestWrapper;

import java.util.List;

@Getter
@Setter
@ToString
public class TaskCreateDTO {

    private Long departmentSeq;
    private Long templateSeq;
    private TaskType taskType;
    private String taskTitle;
    private String taskContent;
    private String FileName;

    private List<EvalListCreateDTO> evalIndicators;

    private TaskRequestWrapper tasks;
}