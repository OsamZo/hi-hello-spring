package spring.hi_hello_spring.onboarding.command.domain.repository;

import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.OnboardingStatus;

import java.util.Optional;

public interface OnboardingStatusRepository {

    OnboardingStatus save(OnboardingStatus onboardingStatus);
    Optional<OnboardingStatus> findByEmployeeSeqAndTemplateSeq(Long employeeSeq, Long templateSeq);
}
