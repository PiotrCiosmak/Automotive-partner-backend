package com.ciosmak.automotivepartner.photo.domain;

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
@Table(name = "photos")
public class Photo extends AbstractEntity
{
    @ToString.Include
    @Column(name = "url", nullable = false)
    private String url;

    // "SHIFT_START"
    // "SHIFT_END"
    // "SHIFT_START_MILEAGE"
    // "SHIFT_END_MILEAGE"
    // "INVOICE"
    // "CAR_DAMAGE"
    // "STATEMENT"
    @ToString.Include
    @Column(name = "type", nullable = false)
    private String type;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "shift_id")
    private Shift shift;
}
