package com.dukaan.userservice.domain.model.dto;

import com.dukaan.userservice.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {

    private Long userId;

    private String userName;

    private String email;

    private String phoneNumber;

    private Set<Role> roles;

}
