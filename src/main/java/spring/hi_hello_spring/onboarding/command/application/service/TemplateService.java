package spring.hi_hello_spring.onboarding.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.onboarding.command.application.dto.TeamplateOrderUpdateDTO;
import spring.hi_hello_spring.onboarding.command.application.dto.TemplateCreateDTO;
import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.Template;
import spring.hi_hello_spring.onboarding.command.domain.repository.TemplateRepository;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final ModelMapper modelMapper;

    /* 온보딩 스토리 보드 등록 */
    @Transactional
    public TemplateCreateDTO createTemplate(TemplateCreateDTO createDTO){

        Template template = modelMapper.map(createDTO, Template.class);
        templateRepository.save(template);
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
    public TeamplateOrderUpdateDTO updateOrderTemplate(Long templateSeq, TeamplateOrderUpdateDTO updateDTO){

        Template template = templateRepository.findById(templateSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));
        modelMapper.map(updateDTO, template);

        return modelMapper.map(template, TeamplateOrderUpdateDTO.class);
    }
}
