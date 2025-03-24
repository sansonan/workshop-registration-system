package com.example.school.security;


import com.example.school.jwt.FilterChainExceptionHandler;
import com.example.school.jwt.JwtLoginFilter;
import com.example.school.jwt.TokenVerifyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private FilterChainExceptionHandler filterChainExceptionHandler;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .addFilter(new JwtLoginFilter(authenticationManager(authenticationConfiguration)))
//                .addFilterBefore(filterChainExceptionHandler, JwtLoginFilter.class)
//                .addFilterAfter(new TokenVerifyFilter(), JwtLoginFilter.class)
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeHttpRequests()
//                .requestMatchers("/login", "/register", "/css/**", "/js/**").permitAll() // Allow these paths without authentication
//                .anyRequest().authenticated() // All other paths require authentication
//                .and()
//                .formLogin()
//                .loginPage("/login") // Custom login page
//                .defaultSuccessUrl("/dashboard", true) // Redirect after successful login
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login?logout")
//                .permitAll();
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilter(new JwtLoginFilter(authenticationManager(authenticationConfiguration)))
                .addFilterBefore(filterChainExceptionHandler, JwtLoginFilter.class)
                .addFilterAfter(new TokenVerifyFilter(), JwtLoginFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/","index.html","css/**","js/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/user/**").hasAuthority(PermissionEnum.CREATE_USER.getDescription())
                .anyRequest()
                .authenticated();

        return http.build();
    }



    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getAuthenticationProvider());
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider getAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

}
