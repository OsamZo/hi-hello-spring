package spring.hi_hello_spring.evaluation.command.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.common.aggregate.entity.File;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.common.repository.FileRepository;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.evaluation.command.application.dto.TaskCreateDTO;
import spring.hi_hello_spring.evaluation.command.application.dto.TaskSubmitDTO;
import spring.hi_hello_spring.evaluation.command.application.dto.TaskUpdateDTO;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.Task;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.TaskSubmit;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.TaskType;
import spring.hi_hello_spring.evaluation.command.domain.repository.EvalListRepository;
import spring.hi_hello_spring.evaluation.command.domain.repository.TaskRepository;
import spring.hi_hello_spring.evaluation.command.domain.service.EvalListDomainService;
import spring.hi_hello_spring.group.command.application.service.GroupMatchService;
import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.Template;
import spring.hi_hello_spring.onboarding.command.domain.repository.TemplateRepository;
import spring.hi_hello_spring.onboarding.command.domain.service.TemplateDomainService;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final EvalListRepository evalListRepository;
    private final TaskRepository taskRepository;
    private final FileRepository fileRepository;
    private final TemplateRepository templateRepository;
    private final ModelMapper modelMapper;
    private final EvalListDomainService evalListDomainService;
    private final TemplateDomainService templateDomainService;
    private final GroupMatchService groupMatchService;

    // 과제 등록
    public void createTask(TaskCreateDTO taskCreateDTO, String uploadFile) {

        Task task = Task.builder()
                .departmentSeq(taskCreateDTO.getDepartmentSeq())
                .templateSeq(taskCreateDTO.getTemplateSeq())
                .taskType(taskCreateDTO.getTaskType())
                .taskTitle(taskCreateDTO.getTaskTitle())
                .taskContent(taskCreateDTO.getTaskContent())
                .build();


        Task savedTask = taskRepository.save(task);

        if (savedTask.getTaskSeq() == null) {
            throw new RuntimeException("taskSeq is null, task could not be saved properly.");
        }

        if (uploadFile != null) {
            File file = File.builder()
                    .taskSeq(savedTask.getTaskSeq())
                    .fileName(taskCreateDTO.getFileName())
                    .fileUrl(uploadFile)
                    .build();
            fileRepository.save(file);
        }

        evalListDomainService.createTask(taskCreateDTO, savedTask);

        groupMatchService.createMenteeGroup(savedTask.getTaskSeq(), taskCreateDTO.getTasks().getTasks()); // passing taskSeq
    }


    // 과제 수정
    @Transactional
    public void updateTask(TaskUpdateDTO taskUpdateDTO, Long taskSeq, String uploadFile) {

        Template template = templateRepository.findByTemplateSeq(taskUpdateDTO.getTemplateSeq());

        Task task = Task.builder()
                .taskSeq(taskSeq)  // 기존 taskSeq 사용
                .taskType(taskUpdateDTO.getTaskType())
                .departmentSeq(taskUpdateDTO.getDepartmentSeq())
                .templateSeq(taskUpdateDTO.getTemplateSeq())
                .taskTitle(taskUpdateDTO.getTaskTitle())
                .taskContent(taskUpdateDTO.getTaskContent())
                .build();
        Task updateTask = taskRepository.save(task);

        if (uploadFile != null) {
            File file = File.builder()
                    .taskSubmitSeq(updateTask.getTaskSeq())
                    .fileUrl(uploadFile)
                    .build();
            fileRepository.save(file);
        }

        // EvalList 항목들 저장
        evalListDomainService.updateTask(taskUpdateDTO, task);
    }

    // 과제 삭제
    @Transactional
    public void deleteTask(Long taskSeq) {
        if(taskRepository.existsById(taskSeq)) {
            taskRepository.deleteById(taskSeq);
        }else{
            throw new CustomException(ErrorCodeType.DATA_NOT_FOUND);
        }
    }

    // 과제 제출
    @Transactional
    public void submitTask(TaskSubmitDTO taskSubmitDTO, Long taskSeq, String uploadFile) {

        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();

        TaskSubmit taskSubmit = TaskSubmit.builder()
                .taskSeq(taskSeq)
                .employeeSeq(employeeSeq)
                .taskSubmitContent(taskSubmitDTO.getTaskSubmitContent())
                .build();
        TaskSubmit saveSubmit = taskRepository.save(taskSubmit);

        if (uploadFile != null) {
            File file = File.builder()
                    .taskSubmitSeq(saveSubmit.getTaskSubmitSeq())
                    .fileName(taskSubmitDTO.getFileName())
                    .fileUrl(uploadFile)
                    .build();
            fileRepository.save(file);
        }


    }
}
