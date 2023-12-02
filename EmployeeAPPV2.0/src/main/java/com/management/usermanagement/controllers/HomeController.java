//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.management.usermanagement.controllers;

import com.management.usermanagement.models.Employee;
import com.management.usermanagement.repositories.EmployeeRepository;
import com.management.usermanagement.security.CustomUserDetails;
import com.management.usermanagement.services.EmployeeService;
import jakarta.servlet.http.HttpSession;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;

    public HomeController() {
    }

    @ModelAttribute
    private void userDetais(Model model, Principal p) {
        if (p != null) {
            String email = p.getName();
            Employee loggedEmployee = this.employeeRepository.findByEmail(email);
            model.addAttribute("loggedUser", loggedEmployee);
        }

    }


    public String showEmployeess() {
        return "index";
    }


    @GetMapping({"/"})
    public String index() {
        return "index";
    }

    @GetMapping({"/signin"})
    public String login() {
        return "login";
    }

    @GetMapping({"/register"})
    public String register() {
        return "register";
    }

    @PostMapping({"/createUser"})
    public String createUser(@ModelAttribute Employee employee, HttpSession session) {
        if (this.employeeService.checkEmail(employee.getEmail())) {
            session.setAttribute("msg", "Email already exists");
        } else {
            Employee employee1 = this.employeeService.createUser(employee);
            if (employee1 != null) {
                session.setAttribute("msg", "Employee registered");
            } else {
                session.setAttribute("msg", "Something went wrong");
            }
        }

        return "redirect:/register";
    }
}
