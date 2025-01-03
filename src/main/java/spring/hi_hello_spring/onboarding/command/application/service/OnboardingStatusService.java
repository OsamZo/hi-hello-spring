package spring.hi_hello_spring.onboarding.command.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.Mentoring;
import spring.hi_hello_spring.mentoring.command.domain.repository.MentoringRepository;
import spring.hi_hello_spring.notify.command.application.service.NotifyService;
import spring.hi_hello_spring.notify.command.domain.aggregate.entity.NotiType;
import spring.hi_hello_spring.onboarding.command.application.dto.ReqOnboardingStatusDTO;
import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.OnboardingStatus;
import spring.hi_hello_spring.onboarding.command.domain.repository.OnboardingStatusRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OnboardingStatusService {

    private final OnboardingStatusRepository onboardingStatusRepository;
    private final MentoringRepository mentoringRepository;
    private final NotifyService notifyService;

    @Transactional
    public void updateOnboardingStatus(Long templateSeq) {

        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();

        OnboardingStatus onboardingStatus = onboardingStatusRepository.findByEmployeeSeqAndTemplateSeq(employeeSeq, templateSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        if (onboardingStatus.getOnboardingCompletedStatus() != null && onboardingStatus.getOnboardingCompletedStatus()) {
            onboardingStatus.updateOnboardingCompletedStatus(false);
        } else {
            onboardingStatus.updateOnboardingCompletedStatus(true);
            Mentoring mentoring = mentoringRepository.findByMenteeSeq(employeeSeq);
            notifyService.send(mentoring.getMenteeSeq(), mentoring.getMentorSeq(), NotiType.ONBOARDING_COMPLETE_BY_MENTEE, "");
        }
        onboardingStatusRepository.save(onboardingStatus);
    }

    @Transactional
    public void updateOnboardingStatusByMentor(Long templateSeq) {

        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();

        Mentoring mentoring = mentoringRepository.findByMentorSeqAndMentoringActiveStatusIsTrue(employeeSeq);

        OnboardingStatus onboardingStatus = onboardingStatusRepository.findByEmployeeSeqAndTemplateSeq(mentoring.getMenteeSeq(), templateSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        if (onboardingStatus.getOnboardingCompletedStatus() != null && onboardingStatus.getOnboardingCompletedStatus()) {
            onboardingStatus.updateOnboardingCompletedStatus(false);
        } else {
            onboardingStatus.updateOnboardingCompletedStatus(true);
            notifyService.send(mentoring.getMentorSeq(), mentoring.getMenteeSeq(), NotiType.ONBOARDING_COMPLETE_BY_MENTOR, "");
        }
        onboardingStatusRepository.save(onboardingStatus);


    }
}


