package com.management.usermanagement.controllers;

import com.management.usermanagement.models.Department;
import com.management.usermanagement.models.Employee;
import com.management.usermanagement.models.Role;
import com.management.usermanagement.repositories.DepartmentRepository;
import com.management.usermanagement.repositories.EmployeeRepository;
import com.management.usermanagement.repositories.RoleRepository;
import com.management.usermanagement.security.CustomUserDetails;
import com.management.usermanagement.services.EmployeeService;
import com.management.usermanagement.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping({"/director"})
public class DirectorController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleRepository roleRepository;

    public DirectorController(){
    }


    @PostMapping("/assignRoleToEmployee")
    @ResponseBody
    public ResponseEntity<String> assignRole(@RequestBody List<Object> requestData){


        if(requestData.size() == 2 && requestData.get(0) instanceof List<?> && requestData.get(1) instanceof Map<?,?>){
            try {
                List<Object> employeeList = (List<Object>) requestData.get(0);
                Map<String, String> adminMap = (Map<String, String>) employeeList.get(0);
                Map<String, String> employeeMap = (Map<String, String>) employeeList.get(1);
                Employee admin = employeeRepository.findByEmail(adminMap.get("email"));
                Employee employee = employeeRepository.findByEmail(employeeMap.get("email"));

                Map<String, String> roleMap = (Map<String, String>) requestData.get(1);
                Role role = this.roleRepository.getRoleByDescription(roleMap.get("description"));
                CustomUserDetails customUserDetails = new CustomUserDetails(admin);
                if(customUserDetails.hasRole("ADMIN")) {
                    roleService.assignUserRole(employee.getId(),role.getId());
                    return new ResponseEntity<>("OK",HttpStatus.OK);
                }
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/unassignRoleFromEmployee")
    @ResponseBody
    public ResponseEntity<String> unassignRole(@RequestBody List<Object> requestData){


        if(requestData.size() == 2 && requestData.get(0) instanceof List<?> && requestData.get(1) instanceof Map<?,?>){
            try {
                List<Object> employeeList = (List<Object>) requestData.get(0);
                Map<String, String> adminMap = (Map<String, String>) employeeList.get(0);
                Map<String, String> employeeMap = (Map<String, String>) employeeList.get(1);
                Employee admin = employeeRepository.findByEmail(adminMap.get("email"));
                Employee employee = employeeRepository.findByEmail(employeeMap.get("email"));

                Map<String, String> roleMap = (Map<String, String>) requestData.get(1);
                Role role = this.roleRepository.getRoleByDescription(roleMap.get("description"));
                CustomUserDetails customUserDetails = new CustomUserDetails(admin);
                if(customUserDetails.hasRole("ADMIN")) {
                    roleService.unassignUserRole(employee.getId(),role.getId());
                    return new ResponseEntity<>("OK",HttpStatus.OK);
                }
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }



    @PostMapping("/getEmployeeNotRoles")
    @ResponseBody
    public ResponseEntity<List<Role>> getUserNotRoles(@RequestBody List<Employee> employees){
        Employee admin = this.employeeRepository.findByEmail(employees.get(0).getEmail());
        Employee employee = this.employeeRepository.findByEmail(employees.get(1).getEmail());
        CustomUserDetails customUserDetails = new CustomUserDetails(admin);
        if(customUserDetails.hasRole("ADMIN")) {
            return new ResponseEntity<>(roleService.getUserNotRoles(employee),HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/getEmployeeRoles")
    @ResponseBody
    public ResponseEntity<Set<Role>> getUserRoles(@RequestBody List<Employee> employees){
        Employee admin = this.employeeRepository.findByEmail(employees.get(0).getEmail());
        Employee employee = this.employeeRepository.findByEmail(employees.get(1).getEmail());
        CustomUserDetails customUserDetails = new CustomUserDetails(admin);
        if(customUserDetails.hasRole("ADMIN")) {
            return new ResponseEntity<>(roleService.getUserRoles(employee),HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping({"/showEmployees"})
    @ResponseBody
    public ResponseEntity<List<Employee>> showEmployees(@RequestBody Employee loginRequest) {
        String email = loginRequest.getEmail();
        Employee loggedEmployee = this.employeeRepository.findByEmail(email);
        CustomUserDetails customUserDetails = new CustomUserDetails(loggedEmployee);
        if(customUserDetails.hasRole("ADMIN")){
            return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping({"/showDepartments"})
    @ResponseBody
    public ResponseEntity<List<Department>> getDepartments(@RequestBody Employee loginRequest) {
        String email = loginRequest.getEmail();
        Employee loggedEmployee = this.employeeRepository.findByEmail(email);
        CustomUserDetails customUserDetails = new CustomUserDetails(loggedEmployee);
        if(customUserDetails.hasRole("ADMIN")){
            return new ResponseEntity<>(departmentRepository.findAll(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody Employee loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Employee loggedEmployee = this.employeeRepository.findByEmail(email);
        CustomUserDetails customUserDetails = new CustomUserDetails(loggedEmployee);
        if(employeeRepository.findByEmail(email) instanceof Employee){
            if(employeeService.isPasswordCorrect(password,customUserDetails.getPassword())){
                if(customUserDetails.hasRole("ADMIN")){
                    return new ResponseEntity<>("ADMIN", HttpStatus.FOUND);
                }{
                    return new ResponseEntity<>("NOT ADMIN", HttpStatus.UNAUTHORIZED);
                }
            }else{
                return new ResponseEntity<>("INCORRECT PASSWORD", HttpStatus.NOT_ACCEPTABLE);
            }
        }
        return new ResponseEntity<>("USER NOT FOUND", HttpStatus.NOT_FOUND);
    }
}
