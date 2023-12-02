//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.management.usermanagement.security;

import com.management.usermanagement.models.Employee;
import com.management.usermanagement.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public UserDetailsServiceImpl() {
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = this.employeeRepository.findByEmail(email);
        if (employee != null) {
            return new CustomUserDetails(employee);
        } else {
            throw new UsernameNotFoundException("Employee not found");
        }
    }
}
