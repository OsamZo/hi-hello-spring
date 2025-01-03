package spring.hi_hello_spring.onboarding.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.onboarding.command.application.dto.CheckListUpdateDTO;
import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.CheckListStatus;
import spring.hi_hello_spring.onboarding.command.domain.repository.CheckListStatusRepository;


@Service
@RequiredArgsConstructor
public class CheckListService {

    private final CheckListStatusRepository checkListStatusRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void checkListUpdate(CheckListUpdateDTO checklistUpdateDTO) {
        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();

        CheckListStatus checkListStatus = CheckListStatus.builder()
                .checklistStatusSeq(checklistUpdateDTO.getChecklistStatusSeq())
                .employeeSeq(employeeSeq)
                .checklistSeq(checklistUpdateDTO.getChecklistSeq())
                .listCheckedStatus(checklistUpdateDTO.getListCheckedStatus())
                .build();
        checkListStatusRepository.save(checkListStatus);
    }
}
