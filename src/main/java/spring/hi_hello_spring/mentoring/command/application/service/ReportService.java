package spring.hi_hello_spring.mentoring.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Employee;
import spring.hi_hello_spring.employee.command.domain.repository.EmployeeRepository;
import spring.hi_hello_spring.mentoring.command.application.dto.WriteFeedbackDTO;
import spring.hi_hello_spring.mentoring.command.application.dto.WriteReportDTO;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.Mentoring;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.Report;
import spring.hi_hello_spring.mentoring.command.domain.repository.MentoringRepository;
import spring.hi_hello_spring.mentoring.command.domain.repository.ReportRepository;
import spring.hi_hello_spring.notify.command.application.service.NotifyService;

import java.util.Objects;
import java.util.Optional;

import static spring.hi_hello_spring.notify.command.domain.aggregate.entity.NotiType.WRITTEN_REPORT_BY_MENTEE;

@Service
@RequiredArgsConstructor
public class ReportService {

    public final MentoringRepository mentoringRepository;
    public final ReportRepository reportRepository;
    public final ModelMapper modelMapper;
    public final NotifyService notifyService;

    @Transactional
    public void createReport(Long employeeSeq, WriteReportDTO writeReportDTO) {

        Mentoring mentoring = mentoringRepository.findByMenteeSeq(employeeSeq);
        Report report = modelMapper.map(writeReportDTO, Report.class);

        Optional<Report> recentReportOpt = reportRepository.findFirstByMentoringSeqOrderByReportWeekDesc(mentoring.getMentoringSeq());

        if (recentReportOpt.isPresent() && recentReportOpt.get().getReportWeek() == report.getReportWeek()) {
            throw new CustomException(ErrorCodeType.DUPLICATE_DATA);
        }
        report.forGroup(mentoring.getMentoringSeq());
        Report currentReport = reportRepository.save(report);

        notifyService.send(employeeSeq, mentoring.getMentorSeq(), WRITTEN_REPORT_BY_MENTEE
                , "/mentoring/report/" + currentReport.getReportSeq());
    }

    @Transactional
    public void modifyReport(Long employeeSeq, Long reportSeq, WriteReportDTO writeReportDTO) {

        Mentoring mentoring = mentoringRepository.findByMenteeSeq(employeeSeq);

        Report report = reportRepository.findByReportSeq(reportSeq);

        if (Objects.equals(report.getMentoringSeq(), mentoring.getMentoringSeq())) {
            modelMapper.map(writeReportDTO, report);
            reportRepository.save(report);
        } else {
            throw new CustomException(ErrorCodeType.COMMON_ERROR);
        }

    }

    @Transactional
    public void writeFeedback(Long employeeSeq, Long reportSeq, WriteFeedbackDTO writeFeedbackDTO) {

        Report report = reportRepository.findByReportSeq(reportSeq);

        Mentoring mentoring = mentoringRepository.findByMentoringSeq(report.getMentoringSeq());

        if (Objects.equals(employeeSeq, mentoring.getMentorSeq())) {
            report.updateFeedbackContent(writeFeedbackDTO.getReportFeedbackContent());
            reportRepository.save(report);
        } else {
            throw new CustomException(ErrorCodeType.COMMON_ERROR);
        }

    }
}
