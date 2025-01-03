package spring.hi_hello_spring.onboarding.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "onboarding_status")
@NoArgsConstructor
@Getter
public class OnboardingStatus extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long onboardingStatusSeq;

    private Long templateSeq;

    private Long employeeSeq;

    private Boolean onboardingCompletedStatus = false;

    public void updateOnboardingCompletedStatus(boolean onboardingCompletedStatus) {
        this.onboardingCompletedStatus = onboardingCompletedStatus;
    }

    @Builder
    public OnboardingStatus(Long templateSeq, Long employeeSeq) {
        this.templateSeq = templateSeq;
        this.employeeSeq = employeeSeq;
    }
}
