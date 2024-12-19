package spring.hi_hello_spring.employee.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.employee.command.application.dto.hr.CreateEmplReqDTO;
import spring.hi_hello_spring.employee.command.application.dto.hr.ModifyEmplReqDTO;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Department;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Employee;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Position;
import spring.hi_hello_spring.employee.command.domain.repository.DepartmentRepository;
import spring.hi_hello_spring.employee.command.domain.repository.EmployeeRepository;
import spring.hi_hello_spring.employee.command.domain.repository.PositionRepository;

@Service
@RequiredArgsConstructor
public class HRService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void createEmpl(CreateEmplReqDTO createEmplReqDTO) {

        Department department = departmentRepository.findByDepartmentName(createEmplReqDTO.getDepartmentName());
        Position position = positionRepository.findByPositionName(createEmplReqDTO.getPositionName());

        String employeePassword = passwordEncoder.encode(createEmplReqDTO.getEmployeeNum());

        Employee newEmployee = modelMapper.map(createEmplReqDTO, Employee.class);
        newEmployee.matchesReq(department.getDepartmentSeq(), position.getPositionSeq(), employeePassword);

        employeeRepository.save(newEmployee);
    }

    @Transactional
    public void modifyEmpl(Long employeeSeq, ModifyEmplReqDTO modifyEmplReqDTO) {

        Employee employee = employeeRepository.findByEmployeeSeq(employeeSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.USER_NOT_FOUND));

        modelMapper.map(modifyEmplReqDTO, employee);
        employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmpl(Long employeeSeq) {

        employeeRepository.deleteById(employeeSeq);
    }
}
