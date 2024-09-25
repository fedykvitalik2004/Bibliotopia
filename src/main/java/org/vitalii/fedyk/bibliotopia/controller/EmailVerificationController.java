package org.vitalii.fedyk.bibliotopia.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vitalii.fedyk.bibliotopia.service.EmailVerificationService;

@RestController
@RequestMapping("/api/v1/email-verification")
@RequiredArgsConstructor
public class EmailVerificationController {
    private final EmailVerificationService emailVerificationService;

    @PostMapping("/verify")
    public ResponseEntity<Void> verifyEmail(@RequestParam("token") final String token) {
        emailVerificationService.verifyByToken(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> createEmailVerification(@PathVariable("userId") final long userId) {
       return ResponseEntity.ok(emailVerificationService.createEmailVerification(userId));
    }
}