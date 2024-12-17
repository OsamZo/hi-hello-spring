package spring.hi_hello_spring.evaluation.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.common.s3.FileUploadUtil;
import spring.hi_hello_spring.evaluation.command.application.dto.TaskCreateDTO;
import spring.hi_hello_spring.evaluation.command.application.dto.TaskSubmitDTO;
import spring.hi_hello_spring.evaluation.command.application.dto.TaskUpdateDTO;
import spring.hi_hello_spring.evaluation.command.application.service.TaskService;

import java.io.IOException;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@Tag(name = "Task API", description = "과제 관련 API")
public class TaskController {

    private final TaskService taskService;
    private final FileUploadUtil fileUploadUtil;

    // 과제 등록
    @PostMapping(value = "/task", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "과제 생성", description = "과제를 생성하는 기능입니다.")
    public ApiResponse<?> createTask(
            @RequestPart("taskCreateDTO") TaskCreateDTO taskCreateDTO,
            @RequestPart(value = "fileUrl", required = false) MultipartFile fileUrl) {

        try {
            // 파일 업로드 처리
            String uploadFile = null;

            // 파일이 존재하는 경우에만 업로드 처리
            if (fileUrl != null && !fileUrl.isEmpty()) {
                uploadFile = fileUploadUtil.uploadFile(fileUrl);
            }

            // 서비스 호출
            taskService.createTask(taskCreateDTO, uploadFile);
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 중 오류 발생", e);
        }

        return ResponseUtil.successResponse("과제가 성공적으로 등록되었습니다.").getBody();
    }

    // 과제 수정
    @PutMapping(value = "/task/{taskSeq}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "과제 수정", description = "과제를 수정하는 기능입니다.")
    public ApiResponse<?> updateTask(@RequestPart("updateTaskDTO") TaskUpdateDTO taskUpdateDTO,
                                     @PathVariable("taskSeq") Long taskSeq,
                                     @RequestPart(value = "fileUrl", required = false) MultipartFile fileUrl) {
        try {
            String uploadFile = null;

            // 파일이 존재하는 경우에만 업로드 처리
            if (fileUrl != null && !fileUrl.isEmpty()) {
                uploadFile = fileUploadUtil.uploadFile(fileUrl);
            }

            // 서비스 호출
            taskService.updateTask(taskUpdateDTO, taskSeq, uploadFile);
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 중 오류 발생", e);
        }
        return ResponseUtil.successResponse("과제가 성공적으로 수정되었습니다.").getBody();
    }

    // 과제 삭제
    @DeleteMapping("/task/{taskSeq}")
    @Operation(summary = "과제 삭제", description = "과제를 삭제하는 기능입니다.")
    public ApiResponse<?> updateTask(@PathVariable Long taskSeq) {
        taskService.deleteTask(taskSeq);
        return ResponseUtil.successResponse("과제가 성공적으로 삭제되었습니다.").getBody();
    }

    // 과제 제출
    @PostMapping(value = "/mentee/task/{taskSeq}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "과제 제출", description = "멘티가 과제를 제출하는 기능입니다.")
    public ApiResponse<?> createSubmit(@RequestPart("createSubmitDTO") TaskSubmitDTO taskSubmitDTO,
                                     @PathVariable("taskSeq") Long taskSeq,
                                     @RequestPart(value = "fileUrl", required = false) MultipartFile fileUrl){

        try {
            // 파일 업로드 처리
            String uploadFile = null;
            if (fileUrl != null && !fileUrl.isEmpty()) {
                uploadFile = fileUploadUtil.uploadFile(fileUrl);
            }
            // 서비스 호출
            taskService.submitTask(taskSubmitDTO, taskSeq, uploadFile);
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 중 오류 발생", e);
        }
        return ResponseUtil.successResponse("과제가 성공적으로 제출되었습니다.").getBody();
    }
}
