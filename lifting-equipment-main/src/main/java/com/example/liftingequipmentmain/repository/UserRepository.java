package com.example.liftingequipmentmain.repository;

import com.example.liftingequipmentmain.model.Role;
import com.example.liftingequipmentmain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByRole(Role role);
}
