package com.dukaan.userservice.application.service;

import com.dukaan.userservice.domain.model.Role;
import com.dukaan.userservice.domain.model.User;
import com.dukaan.userservice.domain.model.dto.AssignRoleDTO;
import com.dukaan.userservice.domain.model.dto.Message;
import com.dukaan.userservice.domain.model.dto.UserProfileDTO;
import com.dukaan.userservice.domain.repository.RoleRepository;
import com.dukaan.userservice.domain.repository.UserRepository;
//import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

//import java.util.Set;

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

        User user = userRepository.findByUserName(username).orElseGet(() -> {
            User newUser = new User();
            newUser.setUserName(username);
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


    public Page<UserProfileDTO> getAllUsers(int page, int size, String sortByParameter, boolean ascending) {

        Sort sort = ascending? Sort.by(sortByParameter).ascending():
                Sort.by(sortByParameter).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageable)
                .map(user -> new UserProfileDTO(user.getUserId(), user.getUserName(), user.getEmail(), user.getPhoneNumber(), user.getRoles()));
    }

    public Message deleteUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return new Message("User deleted Successfully with id "+id);
        } else {
            return new Message("User Not Found with id "+id);
        }
    }

    @Transactional
    public Message deleteUserByUserName(String username) {
        if (userRepository.findByUserName(username).isPresent()) {
            userRepository.deleteByUserName(username);
            return new Message("User deleted Successfully with username "+username);
        } else {
            return new Message("User Not Found with username "+username);
        }
    }

    public UserProfileDTO getProfileDetails(String userName) throws IllegalArgumentException {
        return userRepository.findByUserName(userName)
                .map(user -> new UserProfileDTO(user.getUserId(), user.getUserName(), user.getEmail(), user.getPhoneNumber(), user.getRoles()))
                .orElseThrow(() -> new IllegalArgumentException("username Not found"));
    }

    //Only email and PhoneNumber are updated
    public Message updateProfileDetails(UserProfileDTO profile) throws IllegalArgumentException {
        // Validate username
        if (profile.getUserName() == null || profile.getUserName().isEmpty()) {
            throw new IllegalArgumentException("Invalid username: Username cannot be null or empty.");
        }

        // Fetch the existing user
        User existingUser = userRepository.findByUserName(profile.getUserName())
                .orElseThrow(() -> new IllegalArgumentException("User with username " + profile.getUserName() + " does not exist."));

        // Update allowed fields
        if (profile.getEmail() != null) {
            existingUser.setEmail(profile.getEmail());
        }
        if (profile.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(profile.getPhoneNumber());
        }
        // Add more fields as necessary

        // Save the updated user
        userRepository.save(existingUser);

        return new Message("User updated successfully with the username " + existingUser.getUserName());
    }

    public Message assignRole(AssignRoleDTO assignRoleDTO) {

        String roleName = assignRoleDTO.getRole();

        String userName = assignRoleDTO.getUserName();

        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException("Username not found : "+userName));

        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role not found : "+roleName));

        user.addRole(role);
        userRepository.save(user);

        return new Message("Assigned User "+userName+" with Role "+roleName+" Successfully");
    }

}
