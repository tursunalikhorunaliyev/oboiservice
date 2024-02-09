package com.dataspin.oboiservice.repository;

import com.dataspin.oboiservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getByUsername(String username);
}