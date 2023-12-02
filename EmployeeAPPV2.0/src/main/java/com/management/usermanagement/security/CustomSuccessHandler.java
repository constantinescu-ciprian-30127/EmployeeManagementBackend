//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.management.usermanagement.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public CustomSuccessHandler() {
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        if (userDetails.hasRole("ADMIN")) {
            response.sendRedirect("/admin/");
        } else if (userDetails.hasRole("MANAGER")) {
            response.sendRedirect("/manager/");
        } else if (userDetails.hasRole("EMPLOYEE")) {
            response.sendRedirect("/employee/");
        } else {
            response.sendRedirect("/user/");
        }

    }
}
