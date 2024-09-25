package org.vitalii.fedyk.bibliotopia.service;

public interface EmailVerificationService {
   String createEmailVerification(long userId);
   void verifyByToken(String token);
}