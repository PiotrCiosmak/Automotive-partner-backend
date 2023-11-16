package com.ciosmak.automotivepartner.availability.domain;

import com.ciosmak.automotivepartner.shared.entity.AbstractEntity;
import com.ciosmak.automotivepartner.shift.support.Type;
import com.ciosmak.automotivepartner.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "availability")
public class Availability extends AbstractEntity
{
    @ToString.Include
    @Column(name = "type", nullable = false)
    private Type type;

    @ToString.Include
    @Column(name = "date", columnDefinition = "DATE", nullable = false)
    private LocalDate date;

    @Column(name = "is_used", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isUsed;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
