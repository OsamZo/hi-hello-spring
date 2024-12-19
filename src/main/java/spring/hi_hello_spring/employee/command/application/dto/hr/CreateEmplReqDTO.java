package spring.hi_hello_spring.employee.command.application.dto.hr;

import lombok.*;
import spring.hi_hello_spring.common.aggregate.entity.EmployeeRole;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class CreateEmplReqDTO {

    private String employeeNum;
    private String employeeName;
    private String departmentName;
    private String positionName;
    private String employeePhone;
    private String employeeEmail;
    private EmployeeRole employeeRole;

}
