package com.ciosmak.automotivepartner.token.domain;

import com.ciosmak.automotivepartner.shared.entity.AbstractEntity;
import com.ciosmak.automotivepartner.token.support.TokenType;
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
@Table(name = "tokens")
public class Token extends AbstractEntity
{
    @ToString.Include
    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "type", nullable = false)
    private TokenType type;

    @Column(name = "expiration_time", columnDefinition = "TIMESTAMP ", nullable = false)
    private LocalDateTime expirationTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
