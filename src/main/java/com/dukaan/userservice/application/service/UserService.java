package com.dukaan.userservice.application.service;

import com.dukaan.userservice.domain.model.Role;
import com.dukaan.userservice.domain.model.User;
import com.dukaan.userservice.domain.repository.RoleRepository;
import com.dukaan.userservice.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String username, String password, String roleName) {

//         Finding Role, If not Found Registering new Role
        Role role = roleRepository.findByRoleName(roleName).orElseGet(() -> {
            Role newRole = new Role();
            newRole.setRoleName(roleName);
            roleRepository.save(newRole);
            return newRole;
        });

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Set.of(role));
        userRepository.save(user);
    }
}
