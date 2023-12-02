package com.management.usermanagement.repositories;

import com.management.usermanagement.models.Department;
import com.management.usermanagement.models.Employee;
import com.management.usermanagement.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    boolean existsByDepartmentName(String departmentName);


}
