package org.vitalii.fedyk.bibliotopia.service;

public interface EmailService {
    void sendConfirmationEmail(String firstName, String email, String token);
}
