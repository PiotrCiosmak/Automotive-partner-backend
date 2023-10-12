package com.ciosmak.automotivepartner.token.verification.domain;

import com.ciosmak.automotivepartner.shared.entity.AbstractEntity;
import com.ciosmak.automotivepartner.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "verification-tokens")
public class VerificationToken extends AbstractEntity
{
    @ToString.Include
    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "expiration_time", columnDefinition = "TIMESTAMP ", nullable = false)
    private LocalDateTime expirationTime;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
