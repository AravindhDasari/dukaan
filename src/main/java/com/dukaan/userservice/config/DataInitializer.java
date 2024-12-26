package com.dukaan.userservice.config;

import com.dukaan.userservice.application.service.UserService;
import com.dukaan.userservice.domain.model.Privilege;
import com.dukaan.userservice.domain.model.Role;
import com.dukaan.userservice.domain.model.User;
import com.dukaan.userservice.domain.repository.PrivilegeRepository;
import com.dukaan.userservice.domain.repository.RoleRepository;
import com.dukaan.userservice.domain.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PrivilegeRepository privilegeRepository, UserService userService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findAll().isEmpty() && userRepository.findAll().isEmpty()) {

            // Step 1: Create Privileges and Save Them
            Privilege readPrivilege = new Privilege();
            readPrivilege.setName("READ_PRIVILEGE");
            privilegeRepository.save(readPrivilege);

            Privilege writePrivilege = new Privilege();
            writePrivilege.setName("WRITE_PRIVILEGE");
            privilegeRepository.save(writePrivilege);

            // Step 2: Create Roles and Associate Privileges
            Role adminRole = new Role();
            adminRole.setRoleName("ADMIN");
            adminRole.getPrivileges().add(readPrivilege);
            adminRole.getPrivileges().add(writePrivilege);
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setRoleName("USER");
            userRole.getPrivileges().add(readPrivilege);
            roleRepository.save(userRole);

            // Step 3: Create Users and Associate Roles
            User admin = new User();
            admin.setUserName("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.getRoles().add(adminRole);
            userRepository.save(admin);

            User user = new User();
            user.setUserName("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.getRoles().add(userRole);
            userRepository.save(user);

//            userService.registerUser("sujitha", "sujitha", "USER");
//            userService.registerUser("meghu", "meghu", "USER");
//            userService.registerUser("priyanshu", "priyanshu", "USER");
//            userService.registerUser("axis", "axis", "USER");
//            userService.registerUser("bank", "bank", "USER");
        }
    }
}

