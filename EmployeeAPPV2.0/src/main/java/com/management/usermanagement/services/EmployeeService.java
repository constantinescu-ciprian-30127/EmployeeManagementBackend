//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.management.usermanagement.services;

import com.management.usermanagement.models.Employee;
import com.management.usermanagement.models.Role;
import com.management.usermanagement.repositories.RoleRepository;
import com.management.usermanagement.repositories.EmployeeRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncode;

    public EmployeeService() {
    }

    public Employee findById(int id) {
        return (Employee)this.employeeRepository.findById(id).orElse((Employee) null);
    }

    public Employee createUser(Employee employee) {
        employee.setPassword(this.passwordEncode.encode(employee.getPassword()));
        Set<Role> role = new HashSet();
        role.add((Role)this.roleRepository.getById(2));
        employee.setRoles(role);
        return (Employee)this.employeeRepository.save(employee);
    }

    public boolean isPasswordCorrect(String rawPassword, String encodedPassword) {
        return passwordEncode.matches(rawPassword, encodedPassword);
    }

    public boolean checkEmail(String email) {
        return this.employeeRepository.existsByEmail(email);
    }

    public List<Employee> findAll() {
        return this.employeeRepository.findAll();
    }

    public Employee updateUser(Employee employee) {
        return (Employee)this.employeeRepository.save(employee);
    }
}
