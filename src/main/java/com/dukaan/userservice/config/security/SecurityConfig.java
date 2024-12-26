package com.dukaan.userservice.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    //For Custom Implementation of AuthenticationManager, we need this CustomUserDetailsService
    //Will Use it for later Modification
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Expose a PasswordEncoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Expose an AuthenticationManager bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Configure the security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))
                .formLogin(form -> form.permitAll())
                .logout(logout -> logout.permitAll());
        return http.build();
    }
}
