package spring.hi_hello_spring.employee.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.employee.query.dto.*;
import spring.hi_hello_spring.employee.query.mapper.EmployeeMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeQueryService {

    private final EmployeeMapper employeeMapper;

    public List<MenteeAllQueryDTO> getAllMentee() {

        return employeeMapper.findAllMentee();
    }

    public List<MenteeDepQueryDTO> getDepMentees(Long departmentSeq) {

        List<MenteeDepQueryDTO>  menteeDepQueryDTO = employeeMapper.findMenteeByDep(departmentSeq);
        if(menteeDepQueryDTO == null) {
            throw new IllegalArgumentException("해당 부서의 멘티가 존재하지 않습니다");
        }
        return menteeDepQueryDTO;
    }

    public List<MentorAllQueryDTO> getAllMentor() {
        return employeeMapper.findAllMentor();
    }

    public ReqEmplInfoQueryDTO getEmployeeInfo() {

        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();
        return employeeMapper.findEmployeeInfo(employeeSeq);
    }

    // 멘티/멘토 프로필 조회
    public ReqEmplInfoQueryDTO getMentorInfo(Long mentorSeq) {
        return employeeMapper.findEmployeeInfo(mentorSeq);
    }

    public List<EmployeeListDTO> getEmployeeAll() {
        return employeeMapper.findEmployeeAll();
    }
}
