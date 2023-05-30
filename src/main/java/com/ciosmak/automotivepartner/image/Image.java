package com.ciosmak.automotivepartner.image;

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
@Table(name = "images")
public class Image extends AbstractEntity
{
    @ToString.Include
    @Column(name = "url", nullable = false)
    private String url;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "shift_id")
    private Shift shift;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "image_type_id")
    private ImageType imageType;
}
