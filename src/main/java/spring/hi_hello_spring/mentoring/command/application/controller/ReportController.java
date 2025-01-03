package spring.hi_hello_spring.mentoring.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.mentoring.command.application.dto.WriteFeedbackDTO;
import spring.hi_hello_spring.mentoring.command.application.dto.WriteReportDTO;
import spring.hi_hello_spring.mentoring.command.application.service.ReportService;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@Tag(name = "Mentoring Report API", description = "멘토링 보고서 관련 API")
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "(멘티)보고서 작성", description = "멘티가 보고서를 작성한다.")
    @PostMapping("/mentee/{employeeSeq}/report")
    public ApiResponse<?> writeReport(@PathVariable Long employeeSeq,
                                      @RequestBody WriteReportDTO writeReportDTO) {

        reportService.createReport(employeeSeq, writeReportDTO);
        return ResponseUtil.successResponse("보고서가 작성되었습니다.").getBody();
    }

    @Operation(summary = "(멘티)보고서 수정", description = "멘티가 보고서를 수정한다.")
    @PutMapping("/mentee/{employeeSeq}/report/{reportSeq}")
    public ApiResponse<?> modifyReport(@PathVariable Long employeeSeq, @PathVariable Long reportSeq,
                                       @RequestBody WriteReportDTO writeReportDTO) {

        try {
            reportService.modifyReport(employeeSeq, reportSeq, writeReportDTO);
            return ResponseUtil.successResponse("보고서가 수정되었습니다.").getBody();
        } catch (Exception e) {
            return ResponseUtil.failureResponse("에러가 발생하였습니다. 관리자에게 문의바랍니다.", "COMMON_ERROR").getBody();
        }
    }

    @Operation(summary = "(멘토)피드백 작성/수정", description = "멘토가 보고서에 대한 피드백을 작성, 수정한다.")
    @PutMapping("/mentor/{employeeSeq}/report/{reportSeq}")
    public ApiResponse<?> writeFeedback(@PathVariable Long employeeSeq, @PathVariable Long reportSeq,
                                        @RequestBody WriteFeedbackDTO writeFeedbackDTO) {


        try {
            reportService.writeFeedback(employeeSeq, reportSeq, writeFeedbackDTO);
            return ResponseUtil.successResponse("보고서 피드백이 반영되었습니다.").getBody();
        } catch (Exception e) {
            return ResponseUtil.failureResponse("에러가 발생하였습니다. 관리자에게 문의바랍니다.", "COMMON_ERROR").getBody();
        }
    }

}
