package com.dukaan.userservice.application.service;

import com.dukaan.userservice.domain.model.Role;
import com.dukaan.userservice.domain.model.User;
import com.dukaan.userservice.domain.repository.RoleRepository;
import com.dukaan.userservice.domain.repository.UserRepository;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

//    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
//        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String username, String password, String roleName) throws IllegalAccessException, IllegalArgumentException {

        User user = userRepository.findByUsername(username).orElseGet(() -> {
            User newUser = new User();
            newUser.setUsername(username);
//        user.setPassword(passwordEncoder.encode(password));
            newUser.setPassword(password);
            return newUser;
        });

        if (user.getUserId() != null && !password.equals(user.getPassword())) {
            throw new IllegalAccessException("Existing User, Password Doesn't Match");
        }

        //         Finding Role, If not Found Registering new Role
        Role role = roleRepository.findByRoleName(roleName).orElseGet(() -> {
            Role newRole = new Role();
            newRole.setRoleName(roleName);
            return newRole;
        });

        if(user.getRoles() != null && user.getRoles().contains(role)) {
            throw new IllegalArgumentException("Already Assigned with this Role");
        }

        user.addRole(role);
        roleRepository.save(role);
        userRepository.save(user);

    }
}
