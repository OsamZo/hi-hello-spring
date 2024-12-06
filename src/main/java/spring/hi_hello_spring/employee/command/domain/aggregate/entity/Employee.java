package spring.hi_hello_spring.employee.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;
import spring.hi_hello_spring.common.aggregate.entity.EmployeeRole;

@Entity
@Table(name="employee")
@NoArgsConstructor
@Getter
public class Employee extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeSeq;

    private Long departmentSeq; // 부서 seq

    private Long positionSeq;   // 직급 seq

    private String employeeNum;

    private String employeeName;

    private String employeePhone;

    private String employeeEmail;

    @Enumerated(EnumType.STRING)
    private EmployeeRole employeeRole;  // 역할

    private String employeePassword;

    private Boolean employeeDeletedStatus = false;


    public void matchesReq(Long departmentSeq, Long positionSeq, String employeePassword) {
        this.departmentSeq = departmentSeq;
        this.positionSeq = positionSeq;
        this.employeePassword = employeePassword;
    }
}


