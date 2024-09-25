package org.vitalii.fedyk.bibliotopia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vitalii.fedyk.bibliotopia.entity.EmailVerification;

import java.util.Optional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
    Optional<EmailVerification> findByToken(String token);
}
