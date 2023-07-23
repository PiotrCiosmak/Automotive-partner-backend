package com.ciosmak.automotivepartner.accident.domain;

import com.ciosmak.automotivepartner.entity.AbstractEntity;
import com.ciosmak.automotivepartner.shift.Shift;
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
    @Column(name = "guilty", nullable = false)
    private Boolean guilty;

    @Column(name = "end_of_work", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean endOfWork;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "shift_id")
    private Shift shift;
}
