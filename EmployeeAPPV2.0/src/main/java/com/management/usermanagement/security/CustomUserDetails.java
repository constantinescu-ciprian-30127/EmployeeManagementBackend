//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.management.usermanagement.security;

import com.management.usermanagement.models.Employee;
import com.management.usermanagement.models.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
    private Employee employee;

    public CustomUserDetails(Employee employee) {
        this.employee = employee;
    }

    public boolean hasRole(String role) {
        Iterator var2 = this.employee.getRoles().iterator();

        Role r;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            r = (Role)var2.next();
        } while(!r.getDescription().equals(role));

        return true;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList();
        Iterator var2 = this.employee.getRoles().iterator();

        while(var2.hasNext()) {
            Role role = (Role)var2.next();
            authorities.add(new SimpleGrantedAuthority(role.getDescription()));
        }

        return authorities;
    }

    public String getPassword() {
        return this.employee.getPassword();
    }

    public String getUsername() {
        return this.employee.getEmail();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}
