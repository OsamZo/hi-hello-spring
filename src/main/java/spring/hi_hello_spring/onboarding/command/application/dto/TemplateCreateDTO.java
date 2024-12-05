package spring.hi_hello_spring.onboarding.command.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.TemplateType;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TemplateCreateDTO {

    private Long quizCategorySeq;
    private TemplateType templateType;
    private Boolean templateCheckRequiredStatus;
    private String templateTrainingType;
    private String templateTitle;
    private String templateSub;
    private String templateDetail;
    private String templateUrl;
    private int templateQuizQty;
    private String templateTaskRound;
    private int templateProcedure;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime templateEndAt;
}
