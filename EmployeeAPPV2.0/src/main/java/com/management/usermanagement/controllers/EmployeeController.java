//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.management.usermanagement.controllers;

import com.management.usermanagement.models.Employee;
import com.management.usermanagement.repositories.EmployeeRepository;
import com.management.usermanagement.services.EmployeeService;
import jakarta.servlet.http.HttpSession;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/employee/"})
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public EmployeeController() {
    }

    @ModelAttribute
    private void userDetais(Model model, Principal p) {
        String email = p.getName();
        Employee loggedEmployee = this.employeeRepository.findByEmail(email);
        model.addAttribute("loggedEmployee", loggedEmployee);
        model.addAttribute("home", this.home());
    }

    @GetMapping({"/"})
    public String home() {
        return "employee/home";
    }

    @GetMapping({"/profile"})
    public String profile() {
        return "employee/profile";
    }

    @GetMapping({"/changePassword"})
    public String loadChangePassword() {
        return "employee/change_password";
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

        return "redirect:/employee/changePassword";
    }

    @PostMapping({"/updateProfile"})
    public String updateProfile(@RequestParam("id") int id, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("email") String email, HttpSession httpSession) {
        Employee employee = (Employee)this.employeeRepository.findById(id).orElse((Employee) null);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        Employee updateEmployeeData = (Employee)this.employeeRepository.save(employee);
        if (updateEmployeeData != null) {
            httpSession.setAttribute("msg", "Update");
        } else {
            httpSession.setAttribute("msg", "Something went wrong");
        }

        return "redirect:/employee/profile";
    }
}
