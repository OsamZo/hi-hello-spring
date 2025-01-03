package spring.hi_hello_spring.mentoring.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

import java.time.LocalDate;

@Entity
@Table(name = "report")
@NoArgsConstructor
@Getter
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportSeq;

    private Long mentoringSeq;

    @Lob // 대량 데이터를 처리하도록 지시
    @Column(columnDefinition = "TEXT")
    private String reportContent;

    @Lob // 대량 데이터를 처리하도록 지시
    @Column(columnDefinition = "TEXT")
    private String reportFeeling;

    private int reportWeek;

    @Lob // 대량 데이터를 처리하도록 지시
    @Column(columnDefinition = "TEXT")
    private String reportFeedbackContent;


    public void forGroup(Long mentoringSeq) {
        this.mentoringSeq = mentoringSeq;
    }

    public void updateFeedbackContent(String feedbackContent) {
        this.reportFeedbackContent = feedbackContent;
    }
}
