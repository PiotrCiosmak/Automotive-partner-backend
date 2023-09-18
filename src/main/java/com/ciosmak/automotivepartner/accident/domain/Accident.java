package com.ciosmak.automotivepartner.accident.domain;

import com.ciosmak.automotivepartner.shared.entity.AbstractEntity;
import com.ciosmak.automotivepartner.shift.domain.Shift;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "accidents")
public class Accident extends AbstractEntity
{
    @Column(name = "is_guilty", nullable = false)
    private Boolean isGuilty;

    @Column(name = "is_end_of_work", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isEndOfWork;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "shift_id")
    private Shift shift;
}
