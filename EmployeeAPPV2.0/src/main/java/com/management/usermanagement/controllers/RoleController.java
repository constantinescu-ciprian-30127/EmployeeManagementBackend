//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.management.usermanagement.controllers;

import com.management.usermanagement.services.RoleService;
import com.management.usermanagement.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RoleController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    RoleService roleService;

    public RoleController() {
    }

    @RequestMapping({"/admin/employees/assign/{userId}/{roleId}"})
    public String assignRole(@PathVariable Integer userId, @PathVariable Integer roleId) {
        this.roleService.assignUserRole(userId, roleId);
        return "redirect:/admin/employees/edit/" + userId;
    }

    @RequestMapping({"/admin/employees/unassign/{userId}/{roleId}"})
    public String unassignRole(@PathVariable Integer userId, @PathVariable Integer roleId) {
        this.roleService.unassignUserRole(userId, roleId);
        return "redirect:/admin/employees/edit/" + userId;
    }
}
