package com.dukaan.userservice.config;

import com.dukaan.userservice.application.service.UserService;
import com.dukaan.userservice.domain.model.Role;
import com.dukaan.userservice.domain.repository.RoleRepository;
import com.dukaan.userservice.domain.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, UserService userService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findAll().isEmpty()) {
            Role adminRole = new Role();
            adminRole.setRoleName("ADMIN");
            Role userRole = new Role();
            userRole.setRoleName("USER");
            roleRepository.save(adminRole);
            roleRepository.save(userRole);
        }

        if (userRepository.findAll().isEmpty()) {
            userService.registerUser("sujitha", "sujitha", "USER");
            userService.registerUser("meghu", "meghu", "USER");
            userService.registerUser("priyanshu", "priyanshu", "USER");
            userService.registerUser("axis", "axis", "USER");
            userService.registerUser("bank", "bank", "USER");
            userService.registerUser("admin", "admin123", "ADMIN");
        }
    }
}

