//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.management.usermanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@SuppressWarnings("ALL")
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    public SecurityConfig() {
    }

    @Autowired
    public AuthenticationSuccessHandler customSuccessHandler() {
        return new CustomSuccessHandler();
    }

    @Bean
    public UserDetailsService getUserDetailsServices() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider getDaoAuthentificationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsServices());
        daoAuthenticationProvider.setPasswordEncoder(this.getPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        ((FormLoginConfigurer)
                ((HttpSecurity)
                        ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)
                                ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)
                                        ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)
                                                ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)
                                                        ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)
                ((HttpSecurity)httpSecurity.csrf().disable())
                .authorizeHttpRequests()
                .requestMatchers(new String[]{"/admin/**"})).hasAuthority("ADMIN")
                .requestMatchers(new String[]{"/employee/**"})).hasAuthority("EMPLOYEE")
                .requestMatchers(new String[]{"/manager/**"})).hasAuthority("MANAGER")
                .requestMatchers(new String[]{"/**"})).permitAll().requestMatchers(new String[]{"**/logout"}))
                .permitAll().and()).formLogin().loginPage("/signin").loginProcessingUrl("/login"))
                .successHandler(this.customSuccessHandler());
        return (SecurityFilterChain)httpSecurity.build();
    }
}
