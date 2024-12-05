package spring.hi_hello_spring.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Employee;
import spring.hi_hello_spring.employee.command.domain.repository.EmployeeRepository;
import spring.hi_hello_spring.security.entity.CustomUserDetails;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;


    @Override
    public CustomUserDetails loadUserByUsername(String employeeNum) throws UsernameNotFoundException {

        /* 인증 토큰에 담긴 employeeNum 이 메소드로 넘어오므로 해당 값을 기준으로 DB 에서 조회한다. */
        Employee loginUser = employeeRepository.findByEmployeeNum(employeeNum)
                .orElseThrow(() -> new UsernameNotFoundException(employeeNum));

        // 담당자인지 멘토장인지 멘토인지 멘티인지 확인
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(String.valueOf(loginUser.getEmployeeRole()))); // 역할 -> mentee
        grantedAuthorities.add(new SimpleGrantedAuthority(String.valueOf(loginUser.getDepartmentSeq()))); // 직급Seq

        return new CustomUserDetails(loginUser.getEmployeeSeq(), loginUser.getEmployeeNum(), loginUser.getEmployeePassword(), grantedAuthorities);
    }

    public CustomUserDetails loadUserBySeq(Long employeeSeq) {

        // 요청온 토큰에서 employeeSeq 이 메소드로 넘어오므로 해당 값을 기준으로 DB 에서 조회한다. */
        Employee employee = employeeRepository.findByEmployeeSeq(employeeSeq)
                .orElseThrow(() -> new UsernameNotFoundException(String.valueOf(employeeSeq)));

        // 담당자인지 멘토장인지 멘토인지 멘티인지 확인
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(String.valueOf(employee.getEmployeeRole()))); // 역할 -> mentee
        grantedAuthorities.add(new SimpleGrantedAuthority(String.valueOf(employee.getDepartmentSeq()))); // 직급Seq

        return new CustomUserDetails(employee.getEmployeeSeq(), employee.getEmployeeNum(), employee.getEmployeePassword(), grantedAuthorities);
    }
}
