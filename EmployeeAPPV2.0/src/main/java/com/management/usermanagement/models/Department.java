package com.management.usermanagement.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String departmentName;
    private Integer parentID;
    private Integer managerID;
    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE},
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "department_employee",
            joinColumns = {@JoinColumn(
                    name = "department_id"
            )},
            inverseJoinColumns = {@JoinColumn(
                    name = "employee_id"
            )}
    )
    private Set<Employee> employees = new HashSet<>();
}