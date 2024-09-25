package org.vitalii.fedyk.bibliotopia.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "email_verifications")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class EmailVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private ZonedDateTime expirationDate;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
