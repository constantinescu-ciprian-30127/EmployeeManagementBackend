package com.management.usermanagement.controllers;

import com.management.usermanagement.models.Department;
import com.management.usermanagement.models.Employee;
import com.management.usermanagement.security.CustomUserDetails;
import com.management.usermanagement.services.DepartmentService;
import com.management.usermanagement.services.EmployeeService;
import com.management.usermanagement.services.RoleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    RoleService roleService;

    @PostMapping({"/admin/department/createDepartment"})
    public String createDepartment(@ModelAttribute Department department, HttpSession session) {
        if (this.departmentService.checkDepartment(department.getDepartmentName())) {
            session.setAttribute("msg", "Department already exists");
        }else{
            if(employeeService.findById(department.getManagerID()) == null){
                session.setAttribute("msg", "ID of manager not found");
            }else{
                CustomUserDetails customUserDetails = new CustomUserDetails(employeeService.findById(department.getManagerID()));
                if(customUserDetails.hasRole("MANAGER")){
                    Department department1 = this.departmentService.createDepartment(department);
                    if (department1 != null) {
                        session.setAttribute("msg", "Department created");
                    } else {
                        session.setAttribute("msg", "Something went wrong");
                    }
                }else{
                    session.setAttribute("msg", "The user " + employeeService.findById(department.getManagerID()).getFirstName() + " is not a manager.");
                }
            }
        }
        return "redirect:/admin/createDepartment";
    }

    @RequestMapping({"/admin/dashboard/editDepartment/assignEmployee/{departmentId}/{employeeId}"})
    public String assignEmployee(@PathVariable Integer departmentId, @PathVariable Integer employeeId) {
        this.departmentService.assignEmployee(employeeId, departmentId);
        return "redirect:/admin/dashboard/editDepartment/" + departmentId;
    }

    @RequestMapping({"/admin/dashboard/editDepartment/unassignEmployee/{departmentId}/{employeeId}"})
    public String unassignRole(@PathVariable Integer departmentId, @PathVariable Integer employeeId) {
        this.departmentService.unassignEmployee(employeeId, departmentId);
        return "redirect:/admin/dashboard/editDepartment/" + departmentId;
    }
}
