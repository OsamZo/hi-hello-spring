package spring.hi_hello_spring.employee.command.domain.repository;

import spring.hi_hello_spring.common.aggregate.entity.EmployeeRole;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository{


    Optional<Employee> findByEmployeeNum(String employeeName);

    Optional<Employee> findByEmployeeSeq(Long employeeSeq);

    Employee save(Employee newEmployee);

    void deleteById(Long employeeSeq);

    Optional<Employee> findByDepartmentSeqAndPositionSeq(Long departmentSeq, Long positionSeq);

    List<Employee> findByEmployeeRole(EmployeeRole employeeRole);
}
