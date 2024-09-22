package org.vitalii.fedyk.bibliotopia.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vitalii.fedyk.bibliotopia.dto.AuthenticationRequest;
import org.vitalii.fedyk.bibliotopia.dto.AuthenticationResponse;
import org.vitalii.fedyk.bibliotopia.dto.RegisterRequest;
import org.vitalii.fedyk.bibliotopia.service.AuthenticationService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody final RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody final AuthenticationRequest authenticationRequest) {
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