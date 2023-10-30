package com.ciosmak.automotivepartner.photo.domain;

import com.ciosmak.automotivepartner.photo.support.PhotoType;
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

    @ToString.Include
    @Column(name = "type", nullable = false)
    private PhotoType type;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "shift_id", nullable = false)
    private Shift shift;
}
