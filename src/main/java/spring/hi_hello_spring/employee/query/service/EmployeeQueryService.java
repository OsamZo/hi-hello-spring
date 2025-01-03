package spring.hi_hello_spring.employee.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.employee.query.dto.*;
import spring.hi_hello_spring.employee.query.mapper.EmployeeMapper;
import spring.hi_hello_spring.employee.query.dto.DepartmentListDTO;

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

    public ResEmplInfoQueryDTO getEmployeeInfo() {

        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();
        return employeeMapper.findEmployeeInfo(employeeSeq);
    }

    // 멘토 프로필 조회
    public ResEmplInfoQueryDTO getMentorInfo() {

        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();
        return employeeMapper.findMentorInfo(employeeSeq);
    }

    // 멘티 프로필 조회
    public ResEmplInfoQueryDTO getMenteeInfo() {

        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();
        return employeeMapper.findMenteeInfo(employeeSeq);
    }

    public List<EmployeeListDTO> getEmployeeAll() {
        return employeeMapper.findEmployeeAll();
    }

    public List<EmployeeListDTO> getEmployeeSearch(String searchType, String decodedWord) {

        List<EmployeeListDTO> employees = employeeMapper.getEmployeeSearch(searchType, decodedWord);
        if (employees.isEmpty()) {
            throw new CustomException(ErrorCodeType.DATA_NOT_FOUND);
        }
        return employees;
    }

    public String getEmployeeName(Long employeeSeq) {
        return employeeMapper.findEmployeeName(employeeSeq);
    }

    public List<DepartmentListDTO> getAllDepartment() {
        return employeeMapper.findAllDepartment();
    }

    public List<MentorDepQueryDTO> getDepMentors(Long departmentSeq) {

        List<MentorDepQueryDTO>  mentorDepQueryDTO = employeeMapper.findMentorByDep(departmentSeq);
        if(mentorDepQueryDTO == null) {
            throw new IllegalArgumentException("해당 부서의 멘토가 존재하지 않습니다");
        }
        return mentorDepQueryDTO;
    }

    public List<MenteeDepQueryDTO> getAllDepMentees(Long departmentSeq) {

        List<MenteeDepQueryDTO>  menteeDepQueryDTO = employeeMapper.findAllMenteeByDep(departmentSeq);
        if(menteeDepQueryDTO == null) {
            throw new IllegalArgumentException("해당 부서의 멘티가 존재하지 않습니다");
        }
        return menteeDepQueryDTO;
    }
}
