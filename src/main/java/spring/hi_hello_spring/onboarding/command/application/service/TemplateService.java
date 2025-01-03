package spring.hi_hello_spring.onboarding.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.common.aggregate.entity.EmployeeRole;
import spring.hi_hello_spring.common.aggregate.entity.File;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.common.repository.FileRepository;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.Task;
import spring.hi_hello_spring.evaluation.command.domain.repository.TaskRepository;
import spring.hi_hello_spring.group.command.domain.aggregate.entity.TaskGroup;
import spring.hi_hello_spring.group.command.domain.repository.TaskGroupRepository;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Employee;
import spring.hi_hello_spring.employee.command.domain.repository.EmployeeRepository;
import spring.hi_hello_spring.onboarding.command.application.dto.CheckListCreateDTO;
import spring.hi_hello_spring.onboarding.command.application.dto.TeamplateOrderUpdateDTO;
import spring.hi_hello_spring.onboarding.command.application.dto.TemplateCreateDTO;
import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.*;
import spring.hi_hello_spring.onboarding.command.domain.repository.CheckListStatusRepository;
import spring.hi_hello_spring.onboarding.command.domain.repository.OnboardingStatusRepository;
import spring.hi_hello_spring.onboarding.command.domain.repository.TemplateRepository;
import spring.hi_hello_spring.onboarding.command.infrastructure.repository.JpaChecklistRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final FileRepository fileRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final JpaChecklistRepository checklistRepository;
    private final OnboardingStatusRepository onboardingStatusRepository;
    private final CheckListStatusRepository checkListStatusRepository;
    private final TaskGroupRepository taskGroupRepository;
    private final TaskRepository taskRepository;


    /* 온보딩 스토리 보드 등록 */
    @Transactional
    public TemplateCreateDTO createTemplate(TemplateCreateDTO createDTO, String uploadFile){

        Template template = modelMapper.map(createDTO, Template.class);
        Template savedTemplate = templateRepository.save(template);

        File file = File.builder()
                .templateSeq(savedTemplate.getTemplateSeq())
                .fileName(createDTO.getTemplateUrlName())
                .fileUrl(uploadFile)
                .build();
        fileRepository.save(file);

        // 온보딩 스토리 보드 등록 시 온보딩 수행 상태 테이블에 데이터 삽입
        createOnboardingStatus(savedTemplate);
        return createDTO;
    }

    /* 온보딩 스토리 보드 삭제*/
    @Transactional
    public boolean deleteTemplate(Long templateSeq){

        if(templateRepository.existsById(templateSeq)){
            templateRepository.deleteById(templateSeq);
            return true;
        }else {
            throw new CustomException(ErrorCodeType.DATA_NOT_FOUND);
        }
    }

    /* 온보딩 스토리 보드 순서 변경 */
    @Transactional
    public void updateOrderTemplate(List<TeamplateOrderUpdateDTO> updateDTOs) {
        // 요청받은 각 템플릿 순서를 처리
        for (TeamplateOrderUpdateDTO dto : updateDTOs) {
            Optional<Template> templateOpt = templateRepository.findById(dto.getTemplateSeq()); // templateSeq로 템플릿 조회

            if (templateOpt.isPresent()) {
                Template template = templateOpt.get();

                // ModelMapper를 사용해서 TeamplateOrderUpdateDTO를 Template 엔티티로 매핑
                modelMapper.map(dto, template);

                // 만약 필요한 다른 매핑 항목이 있다면 여기에 설정 가능
                // 예: template.setTemplateProcedure(dto.getTemplateProcedure());

                // 템플릿 저장
                templateRepository.save(template);  // 변경된 템플릿 정보 저장
            }
        }
    }

    @Transactional
    public void createCheckListTemplate(TemplateCreateDTO createDTO) {

        Template template = modelMapper.map(createDTO, Template.class);
        Template savedTemplate = templateRepository.save(template);
        if (createDTO.getChecklistContent() != null && !createDTO.getChecklistContent().isEmpty()) {
            for (CheckListCreateDTO checkList : createDTO.getChecklistContent()) {
                // 체크리스트 엔티티 생성 (Builder 사용)
                System.out.println("hihi"+checkList.getChecklistContent());
                Checklist checkListEntity = Checklist.builder()
                        .checklistContent(checkList.getChecklistContent())  // 체크리스트 내용 설정
                        .templateSeq(savedTemplate.getTemplateSeq())  // 템플릿과 연결
                        .build();

                checklistRepository.save(checkListEntity);  // 체크리스트 저장
            }
        }
        // 온보딩 스토리 보드 등록 시 온보딩 수행 상태 테이블에 데이터 삽입
        createOnboardingStatus(savedTemplate);
    }

    private void createOnboardingStatus(Template savedTemplate) {
        List<Employee> mentees = employeeRepository.findByEmployeeRole(EmployeeRole.MENTEE);

        for (Employee mentee : mentees) {
            OnboardingStatus onboardingStatus = OnboardingStatus
                    .builder()
                    .templateSeq(savedTemplate.getTemplateSeq())
                    .employeeSeq(mentee.getEmployeeSeq())
                    .build();
            onboardingStatusRepository.save(onboardingStatus);

            if(savedTemplate.getTemplateType() == TemplateType.CHECKLIST) {
                List<Checklist> checklists = checklistRepository.findByTemplateSeq(savedTemplate.getTemplateSeq());
                for (Checklist checklist : checklists) {
                    CheckListStatus checklistStatus = CheckListStatus.builder()
                            .employeeSeq(mentee.getEmployeeSeq())
                            .checklistSeq(checklist.getChecklistSeq())
                            .listCheckedStatus(false)
                            .build();
                    checkListStatusRepository.save(checklistStatus);
                }
            }
        }
    }

    @Transactional
    @Scheduled(cron = "0 0 * * * ?") // 매 시간 0분 0초에 실행
    public void updateTaskGroupStatus() {
        // 모든 템플릿에 대해 마감기한을 확인하고, 상태 업데이트
        List<Template> templates = templateRepository.findAll();
        for (Template template : templates) {
            if (template.getTemplateEndAt() != null && template.getTemplateEndAt().isBefore(LocalDateTime.now())) {
            // 템플릿에 해당하는 모든 task를 찾음
                List<Task> tasks = taskRepository.findByTemplateSeq(template.getTemplateSeq());

                for (Task task : tasks) {
                    // 각 task에 해당하는 task_group을 찾아서 상태 업데이트
                    List<TaskGroup> taskGroups = taskGroupRepository.findByTaskSeq(task.getTaskSeq());

                    for (TaskGroup taskGroup : taskGroups) {
                        // 상태를 0으로 업데이트
                        if (taskGroup.getTaskGroupActiveStatus()) {
                            taskGroup.setTaskGroupActiveStatus(false);
                            taskGroupRepository.save(taskGroup);  // 저장
                        }
                    }
                }
            }
        }
    }
}