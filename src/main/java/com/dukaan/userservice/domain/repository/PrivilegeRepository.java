package com.dukaan.userservice.domain.repository;

import com.dukaan.userservice.domain.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
}
