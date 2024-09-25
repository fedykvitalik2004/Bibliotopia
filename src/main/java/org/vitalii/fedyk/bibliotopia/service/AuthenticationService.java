package org.vitalii.fedyk.bibliotopia.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.vitalii.fedyk.bibliotopia.dto.AuthenticationResponse;
import org.vitalii.fedyk.bibliotopia.dto.RegisterRequest;

import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest registerRequest, String language);
    AuthenticationResponse login(String email, String password);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
