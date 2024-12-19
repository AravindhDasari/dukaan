package com.dukaan.userservice.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(unique = true, nullable = false)
    private String roleName;

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
