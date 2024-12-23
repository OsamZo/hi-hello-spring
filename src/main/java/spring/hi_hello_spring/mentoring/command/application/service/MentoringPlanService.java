package spring.hi_hello_spring.mentoring.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import spring.hi_hello_spring.common.aggregate.entity.File;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.common.repository.FileRepository;
import spring.hi_hello_spring.mentoring.command.application.dto.MentoringPlanRequestDTO;
import spring.hi_hello_spring.mentoring.command.application.dto.MentoringPlanUpdateDTO;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.Planning;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.PlanningStatus;
import spring.hi_hello_spring.mentoring.command.domain.repository.PlanningRepository;

@Service
@RequiredArgsConstructor
public class MentoringPlanService {

    private final PlanningRepository planningRepository;
    private final FileRepository fileRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void createMentoringPlan(MentoringPlanRequestDTO mentoringPlanRequestDTO, String uploadFile) {

        Planning planning = Planning.builder()
                .employeeSeq(mentoringPlanRequestDTO.getEmployeeSeq())
                .planningName(mentoringPlanRequestDTO.getPlanningName())
                .planningContent(mentoringPlanRequestDTO.getPlanningContent())
                .build();
        Planning savedPlanning = planningRepository.save(planning);

        File file = File.builder()
                .planningSeq(savedPlanning.getPlanningSeq())
                .fileName(mentoringPlanRequestDTO.getFileName())
                .fileUrl(uploadFile)
                .build();
        fileRepository.save(file);
    }

    @Transactional
    public void deleteMentoringPlan(Long planningSeq) {

        Planning savedPlanning = planningRepository.findById(planningSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        fileRepository.deleteByPlanningSeq(planningSeq);
        planningRepository.delete(savedPlanning);
    }

    public void modifyMentoringPlan(Long planningSeq, MentoringPlanUpdateDTO mentoringPlanUpdateDTO) {

        Planning modifyPlanning = planningRepository.findById(planningSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        modelMapper.map(mentoringPlanUpdateDTO, modifyPlanning);

        planningRepository.save(modifyPlanning);
    }
}
