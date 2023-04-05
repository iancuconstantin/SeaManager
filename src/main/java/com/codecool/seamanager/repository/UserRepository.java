package com.codecool.seamanager.repository;

import com.codecool.seamanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
