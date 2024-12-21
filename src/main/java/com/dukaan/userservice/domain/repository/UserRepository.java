package com.dukaan.userservice.domain.repository;

import com.dukaan.userservice.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String username);

    void deleteByUserName(String username);

}
