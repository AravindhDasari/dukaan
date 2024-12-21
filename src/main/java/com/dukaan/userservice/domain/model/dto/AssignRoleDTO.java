package com.dukaan.userservice.domain.model.dto;

import com.dukaan.userservice.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignRoleDTO {

    private String userName;

    private String role;
}
