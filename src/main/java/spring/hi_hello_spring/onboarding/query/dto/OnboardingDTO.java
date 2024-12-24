package spring.hi_hello_spring.onboarding.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class OnboardingDTO {

    private Long employeeSeq;
    private String employeeName;
    private Long onboardingStatusSeq;
    private boolean onboardingCompletedStatus;

    private Long templateSeq;
    private String templateType;
    private boolean templateCheckRequiredStatus;
    private String templateTrainingType;
    private String templateTitle;
    private String templateSub;
    private String templateDetail;
    private String templateUrlName;

    private Long quizCategorySeq;
    private String quizCategoryName;

    private String templateTaskRound;
    private Long taskSeq;
    private String taskTitle;

    private int templateProcedure;
    private LocalDateTime templateEndAt;

    private Long checklistSeq;
    private String checklistContent;
    private Long checklistStatusSeq;
    private boolean listCheckedStatus;
    private Long taskGroupSeq;
}
