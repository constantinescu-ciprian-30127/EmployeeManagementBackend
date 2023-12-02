//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.management.usermanagement.repositories;

import com.management.usermanagement.models.Employee;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    boolean existsByEmail(String email);

    Employee findByEmail(String email);

    @Query(
            value = "SELECT * FROM employee WHERE id IN (SELECT employee_id FROM employee_role WHERE role_id IN (SELECT id FROM role WHERE description='EMPLOYEE'))",
            nativeQuery = true
    )
    List<Employee> getEmployees();

    @Query(
            value = "SELECT * FROM employee WHERE id NOT IN (SELECT employee_id FROM department_employee WHERE department_id = ?1)",
            nativeQuery = true
    )
    Set<Employee> getDepartmentNotEmployees(Integer departmentId);
}
