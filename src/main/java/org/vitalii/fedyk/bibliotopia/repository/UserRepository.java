package org.vitalii.fedyk.bibliotopia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vitalii.fedyk.bibliotopia.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
