package org.vitalii.fedyk.bibliotopia.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vitalii.fedyk.bibliotopia.constant.ExceptionMessages;
import org.vitalii.fedyk.bibliotopia.dto.AuthenticationResponse;
import org.vitalii.fedyk.bibliotopia.dto.RegisterRequest;
import org.vitalii.fedyk.bibliotopia.entity.User;
import org.vitalii.fedyk.bibliotopia.enumeration.AccountStatus;
import org.vitalii.fedyk.bibliotopia.enumeration.Role;
import org.vitalii.fedyk.bibliotopia.exception.NotFoundException;
import org.vitalii.fedyk.bibliotopia.exception.UserAlreadyRegisteredException;
import org.vitalii.fedyk.bibliotopia.mapper.RegisterRequestMapper;
import org.vitalii.fedyk.bibliotopia.repository.UserRepository;
import org.vitalii.fedyk.bibliotopia.entity.Token;
import org.vitalii.fedyk.bibliotopia.repository.TokenRepository;
import org.vitalii.fedyk.bibliotopia.service.AuthenticationService;
import org.vitalii.fedyk.bibliotopia.service.EmailService;
import org.vitalii.fedyk.bibliotopia.service.EmailVerificationService;
import org.vitalii.fedyk.bibliotopia.service.JwtService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final RegisterRequestMapper registerRequestMapper;
    private final EmailService emailService;
    private final EmailVerificationService emailVerificationService;

    @Override
    public AuthenticationResponse register(final RegisterRequest registerRequest, final String language) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyRegisteredException(ExceptionMessages.USER_ALREADY_REGISTERED
                    .formatted(registerRequest.getEmail()));
        }
        User user = registerRequestMapper.registerRequestToUser(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.ROLE_USER);
        user.setStatus(AccountStatus.EXPECTING_EMAIL_VERIFICATION);

        user = userRepository.save(user);

        final String accessToken = jwtService.generateAccessToken(user);
        final String refreshToken = jwtService.generateRefreshToken(user);

        saveToken(accessToken, refreshToken, user);

        final String emailVerificationToken = emailVerificationService.createEmailVerification(user.getId());
        emailService.sendConfirmationEmail(user.getFullName().getFirstName(), user.getEmail(), emailVerificationToken);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveToken(final String accessToken, final String refreshToken, final User user) {
        final Token token = Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .loggedOut(false)
                .user(user)
                .build();
        tokenRepository.save(token);
    }

    @Override
    public AuthenticationResponse login(final String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        final User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.USER_NOT_FOUND.formatted(email)));

        final String accessToken = jwtService.generateAccessToken(user);
        final String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllTokens(user.getId());
        saveToken(accessToken, refreshToken, user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void revokeAllTokens(final long userId) {
        List<Token> tokens = tokenRepository.findAllValidTokensByUser(userId);
        tokens.forEach((token) -> token.setLoggedOut(true));
    }

    @Override
    public void refreshToken(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            return;
        }
        final String refreshToken = authorizationHeader.substring(7);
        final String username = jwtService.extractUsername(refreshToken);

        final User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.USER_NOT_FOUND.formatted(username)));

        if (jwtService.isValidRefreshToken(refreshToken, user)) {
            final Token token = tokenRepository.findByRefreshToken(refreshToken)
                    .orElseThrow(() -> new NotFoundException(ExceptionMessages.TOKEN_NOT_FOUND));
            final String accessToken = jwtService.generateAccessToken(user);

            token.setAccessToken(accessToken);

            final AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
            adjustResponse(response, authenticationResponse);
        }
    }

    private void adjustResponse(final HttpServletResponse response, final AuthenticationResponse authenticationResponse) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final String json = mapper.writeValueAsString(authenticationResponse);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
    }
}