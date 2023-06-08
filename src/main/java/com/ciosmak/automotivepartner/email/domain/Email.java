package com.ciosmak.automotivepartner.email.domain;

import com.ciosmak.automotivepartner.entity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "emails")
public class Email extends AbstractEntity
{
    @ToString.Include
    @Column(name = "email", nullable = false, unique = true)
    private String email;
}
