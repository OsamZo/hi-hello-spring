package spring.hi_hello_spring.onboarding.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.employee.query.dto.EmployeeRoleDTO;
import spring.hi_hello_spring.employee.query.mapper.EmployeeMapper;
import spring.hi_hello_spring.onboarding.query.dto.TemplateAllQueryDTO;
import spring.hi_hello_spring.onboarding.query.dto.TemplateTaskRoundDTO;
import spring.hi_hello_spring.onboarding.query.mapper.TemplateMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateQueryService {

    private final TemplateMapper templateMapper;
    private final EmployeeMapper employeeMapper;

    /* 온보딩 스토리보드 순서 전체 조회 */
    public List<TemplateAllQueryDTO> getAllTemplate(){

        return templateMapper.findAllTemplate();
    }

    /* 권한 별 템플릿 차수 조회 */
    public List<TemplateTaskRoundDTO> getTemplateTaskRound() {
        Long currentEmployeeSeq = CustomUserUtils.getCurrentEmployeeSeq();

        EmployeeRoleDTO employeeRoleDTO = employeeMapper.findEmployeeRole(currentEmployeeSeq);

        String currentEmployeeRole = employeeRoleDTO.getEmployeeRole().name();
        return templateMapper.findTemplateTaskRound(currentEmployeeRole);
    }
}
