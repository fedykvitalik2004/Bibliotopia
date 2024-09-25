package org.vitalii.fedyk.bibliotopia.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vitalii.fedyk.bibliotopia.constant.ExceptionMessages;
import org.vitalii.fedyk.bibliotopia.entity.EmailVerification;
import org.vitalii.fedyk.bibliotopia.enumeration.AccountStatus;
import org.vitalii.fedyk.bibliotopia.exception.EmailVerificationTokenExpiredException;
import org.vitalii.fedyk.bibliotopia.exception.NotFoundException;
import org.vitalii.fedyk.bibliotopia.repository.EmailVerificationRepository;
import org.vitalii.fedyk.bibliotopia.repository.UserRepository;
import org.vitalii.fedyk.bibliotopia.service.EmailVerificationService;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class EmailVerificationServiceImpl implements EmailVerificationService {
    private final EmailVerificationRepository emailVerificationRepository;
    private final UserRepository userRepository;
    private final int emailVerificationExpiration;

    public EmailVerificationServiceImpl(final EmailVerificationRepository emailVerificationRepository,
                                        final UserRepository userRepository,
                                        @Value("${variables.security.email-verification-expiration}") final int emailVerificationExpiration) {
        this.emailVerificationRepository = emailVerificationRepository;
        this.userRepository = userRepository;
        this.emailVerificationExpiration = emailVerificationExpiration;
    }

    @Override
    public String createEmailVerification(final long userId) {
        final EmailVerification emailVerification = EmailVerification.builder()
                .token(UUID.randomUUID().toString())
                .user(userRepository.getReferenceById(userId))
                .expirationDate(ZonedDateTime.now().plusHours(emailVerificationExpiration))
                .build();
        return emailVerificationRepository.save(emailVerification)
                .getToken();
    }

    @Override
    public void verifyByToken(final String token) {
        final EmailVerification emailVerification = emailVerificationRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.VERIFICATION_TOKEN_NOT_FOUND.formatted(token)));
        if (emailVerification.getExpirationDate().isBefore(ZonedDateTime.now())) {
            throw new EmailVerificationTokenExpiredException(ExceptionMessages.VERIFICATION_TOKEN_EXPIRED);
        }
        emailVerificationRepository.delete(emailVerification);
        emailVerification.getUser().setStatus(AccountStatus.ACTIVE);
        log.info("Successfully verified email: {}", emailVerification.getUser().getEmail());
    }
}
