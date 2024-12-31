package com.dukaan.userservice.config.security;

import com.dukaan.userservice.domain.model.Role;
import com.dukaan.userservice.domain.model.User;
import com.dukaan.userservice.domain.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found "+username));

        Set<Role> roles = user.getRoles();
        for(Role role:roles) {
            System.out.println("Log loadUserByUsername Role Privileges: "+role.getPrivileges());
        }

        return new CustomUserDetails(user);
    }
}
