package org.vitalii.fedyk.bibliotopia.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vitalii.fedyk.bibliotopia.dto.AuthenticationRequest;
import org.vitalii.fedyk.bibliotopia.dto.AuthenticationResponse;
import org.vitalii.fedyk.bibliotopia.dto.RegisterRequest;
import org.vitalii.fedyk.bibliotopia.service.AuthenticationService;

import java.io.IOException;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody final RegisterRequest registerRequest,
                                                           final Locale locale) {
        return ResponseEntity.ok(authenticationService.register(registerRequest, locale.getLanguage()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody final AuthenticationRequest authenticationRequest,
                                                        final Locale locale) {
        return ResponseEntity.ok(
                authenticationService.login(authenticationRequest.email(), authenticationRequest.password())
        );
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Void> refreshToken(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
        return ResponseEntity.ok().build();
    }
}