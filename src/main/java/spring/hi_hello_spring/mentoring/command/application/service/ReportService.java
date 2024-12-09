package spring.hi_hello_spring.mentoring.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.mentoring.command.application.dto.WriteReportDTO;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.Mentoring;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.Report;
import spring.hi_hello_spring.mentoring.command.domain.repository.MentoringRepository;
import spring.hi_hello_spring.mentoring.command.domain.repository.ReportRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {

    public final MentoringRepository mentoringRepository;
    public final ReportRepository reportRepository;
    public final ModelMapper modelMapper;

    public void createReport(Long employeeSeq, WriteReportDTO writeReportDTO) {

        Mentoring mentoring = mentoringRepository.findByMenteeSeq(employeeSeq);
        Report report = modelMapper.map(writeReportDTO, Report.class);

        Optional<Report> recentReportOpt = reportRepository.findByMentoringSeqOrderByReportWeekDesc(mentoring.getMentoringSeq());

        int week = recentReportOpt.map(Report::getReportWeek).orElse(0) + 1;
        report.forGroup(mentoring.getMentoringSeq(), week);

        reportRepository.save(report);
    }
}
