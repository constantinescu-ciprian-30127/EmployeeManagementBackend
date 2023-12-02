//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.management.usermanagement.controllers;

import com.management.usermanagement.models.Department;
import com.management.usermanagement.models.Employee;
import com.management.usermanagement.repositories.DepartmentRepository;
import com.management.usermanagement.repositories.RoleRepository;
import com.management.usermanagement.repositories.EmployeeRepository;
import com.management.usermanagement.services.DepartmentService;
import com.management.usermanagement.services.RoleService;
import com.management.usermanagement.services.EmployeeService;
import jakarta.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"/admin"})
public class AdminController {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public AdminController() {
    }

    @ModelAttribute
    private void userDetais(Model model, Principal p) {
        String email = p.getName();
        Employee loggedEmployee = this.employeeRepository.findByEmail(email);
        model.addAttribute("loggedEmployee", loggedEmployee);
    }

    @GetMapping({"/"})
    public String home() {
        return "admin/home";
    }

    @GetMapping({"/profile"})
    public String profile() {
        return "user/profile";
    }

    @GetMapping({"/changePassword"})
    public String changePassword() {
        return "user/change_password";
    }

    @GetMapping({"/createDepartment"})
    public String createDepartment() {
        return "admin/create_department";
    }



    @GetMapping({"/dashboard"})
    public String showUsers(Model model) {
        model.addAttribute("employees", this.employeeService.findAll());
        model.addAttribute("departments", this.departmentService.findAll());
        return "admin/admin_dashboard";
    }

    @GetMapping({"/employees/edit/{id}"})
    public String editUser(@PathVariable Integer id, Model model) {
        Employee employee = this.employeeService.findById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("userRoles", this.roleService.getUserRoles(employee));
        model.addAttribute("userNotRoles", this.roleService.getUserNotRoles(employee));
        return "admin/edit_employees";
    }

    @RequestMapping(
            value = {"/employees/delete/{id}"},
            method = {RequestMethod.GET, RequestMethod.DELETE}
    )
    public String deleteUser(@PathVariable Integer id, Model model) {
        Employee employee = this.employeeService.findById(id);
        employee.getRoles().removeAll(employee.getRoles());
        this.employeeRepository.delete(employee);
        return "redirect:/admin/dashboard";
    }

    @PostMapping({"/updatePassword"})
    public String changePassword(Principal p, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, HttpSession httpSession) {
        String email = p.getName();
        Employee employee = this.employeeRepository.findByEmail(email);
        if (this.passwordEncoder.matches(oldPassword, employee.getPassword())) {
            employee.setPassword(this.passwordEncoder.encode(newPassword));
            Employee updatePasswordEmployee = (Employee)this.employeeRepository.save(employee);
            if (updatePasswordEmployee != null) {
                httpSession.setAttribute("msg", "Password changed");
            } else {
                httpSession.setAttribute("msg", "Something went wrong");
            }
        } else {
            httpSession.setAttribute("msg", "Incorrect old password");
        }

        return "redirect:/admin/changePassword";
    }

    @PostMapping({"/updateProfile"})
    public String updateProfile(@RequestParam("id") int id, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("email") String email, HttpSession httpSession) {
        Employee employee = (Employee)this.employeeRepository.findById(id).orElse((Employee) null);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        Employee updateEmployeeData = (Employee)this.employeeRepository.save(employee);
        if (updateEmployeeData != null) {
            httpSession.setAttribute("msg", "Updated");
        } else {
            httpSession.setAttribute("msg", "Something went wrong");
        }

        return "redirect:/admin/profile";
    }

    @RequestMapping(
            value = {"/dashboard/deleteDepartment/{id}"},
            method = {RequestMethod.GET, RequestMethod.DELETE}
    )
    public String deleteDepartment(@PathVariable Integer id, Model model) {
        Department department = this.departmentService.findById(id);
        this.departmentRepository.delete(department);
        return "redirect:/admin/dashboard";
    }

    @GetMapping({"/dashboard/editDepartment/{id}"})
    public String editDepartment(@PathVariable Integer id, Model model) {
        Department department = this.departmentService.findById(id);
        Employee manager = (Employee) this.employeeRepository.findById(department.getManagerID()).orElse((Employee) null);
        model.addAttribute("department", department);
        model.addAttribute("manager", manager);
        model.addAttribute("departmentEmployees", this.departmentService.getDepartmentEmployees(department));
        model.addAttribute("departmentNotEmployees", this.departmentService.getDepartmentNotEmployees(department));
        return "admin/edit_department";
    }

}
