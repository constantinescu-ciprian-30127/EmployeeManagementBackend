//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.management.usermanagement.services;

import com.management.usermanagement.models.Employee;
import com.management.usermanagement.models.Role;
import com.management.usermanagement.repositories.RoleRepository;
import com.management.usermanagement.repositories.EmployeeRepository;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public RoleService() {
    }

    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }

    public Role findById(int id) {
        return (Role)this.roleRepository.findById(id).orElse((Role) null);
    }

    public void delete(int id) {
        this.roleRepository.deleteById(id);
    }

    public void save(Role role) {
        this.roleRepository.save(role);
    }

    public void assignUserRole(Integer userId, Integer roleId) {
        Employee employee = (Employee)this.employeeRepository.findById(userId).orElse((Employee) null);
        Role role = (Role)this.roleRepository.findById(roleId).orElse((Role) null);
        Set<Role> userRoles = employee.getRoles();
        userRoles.add(role);
        employee.setRoles(userRoles);
        this.employeeRepository.save(employee);
    }

    public void unassignUserRole(Integer userId, Integer roleId) {
        Employee employee = (Employee)this.employeeRepository.findById(userId).orElse((Employee) null);
        employee.getRoles().removeIf((x) -> {
            return x.getId() == roleId;
        });
        this.employeeRepository.save(employee);
    }

    public Set<Role> getUserRoles(Employee employee) {
        return employee.getRoles();
    }

    public List<Role> getUserNotRoles(Employee employee) {
        return this.roleRepository.getUserNotRoles(employee.getId());
    }
}
