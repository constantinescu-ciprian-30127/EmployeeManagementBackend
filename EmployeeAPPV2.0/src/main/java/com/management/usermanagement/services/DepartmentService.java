package com.management.usermanagement.services;

import com.management.usermanagement.models.Department;
import com.management.usermanagement.models.Employee;
import com.management.usermanagement.models.Role;
import com.management.usermanagement.repositories.DepartmentRepository;
import com.management.usermanagement.repositories.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public DepartmentService(){
    }

    public List<Department> findAll(){
        return departmentRepository.findAll();
    }
    public Department findById(int id) {
        return (Department) this.departmentRepository.findById(id).orElse((Department) null);
    }

    public Department createDepartment(Department department) {
        return (Department) departmentRepository.save(department);
    }


    public boolean checkDepartment(String departmentName) {
        return this.departmentRepository.existsByDepartmentName(departmentName);
    }

    public Set<Employee> getDepartmentEmployees(Department department) {
        return department.getEmployees();
    }

    public Set<Employee> getDepartmentNotEmployees(Department department) {
        return this.employeeRepository.getDepartmentNotEmployees(department.getId());
    }

    public void assignEmployee(Integer employeeId, Integer departmentId) {
        Employee employee = (Employee)this.employeeRepository.findById(employeeId).orElse((Employee) null);
        Department department = (Department) this.departmentRepository.findById(departmentId).orElse((Department) null);
        Set<Employee> employees = department.getEmployees();
        department.setEmployees(null);
        this.departmentRepository.save(department);
        employees.add(employee);
        department.setEmployees(employees);
        this.departmentRepository.save(department);
    }

    public void unassignEmployee(Integer employeeId, Integer departmentId) {
        Department department = (Department) this.departmentRepository.findById(departmentId).orElse((Department) null);
        Set<Employee> employees = department.getEmployees();
        department.setEmployees(null);
        this.departmentRepository.save(department);
        employees.removeIf((x) -> {
            return x.getId() == employeeId;
        });
        department.setEmployees(employees);
        this.departmentRepository.save(department);
    }
}
