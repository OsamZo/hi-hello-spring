package spring.hi_hello_spring.employee.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import spring.hi_hello_spring.employee.query.dto.*;
import spring.hi_hello_spring.employee.query.dto.DepartmentListDTO;

import java.util.List;

@Mapper
public interface EmployeeMapper {


    List<MenteeAllQueryDTO> findAllMentee();

    List<MenteeDepQueryDTO> findMenteeByDep(Long departmentSeq);

    List<MenteeDepQueryDTO> findAllMenteeByDep(Long departmentSeq);

    List<MentorAllQueryDTO> findAllMentor();

    ResEmplInfoQueryDTO findEmployeeInfo(Long employeeSeq);

    List<EmployeeListDTO> findEmployeeAll();

    List<EmployeeListDTO> getEmployeeSearch(@Param("searchType") String searchType, @Param("keyword") String keyword);

    String findEmployeeName(Long employeeSeq);

    ResEmplInfoQueryDTO findMentorInfo(Long employeeSeq);

    ResEmplInfoQueryDTO findMenteeInfo(Long employeeSeq);

    List<DepartmentListDTO> findAllDepartment();

    List<MentorDepQueryDTO> findMentorByDep(Long departmentSeq);

    EmployeeRoleDTO findEmployeeRole(Long currentEmployeeSeq);
}
