package com.ciosmak.automotivepartner.image;

import com.ciosmak.automotivepartner.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "image_types")
public class ImageType extends AbstractEntity
{
    @ToString.Include
    @Column(name = "type", nullable = false)
    private String type;// {SHIFT_START, SHIFT_END, SHIFT_START_MILEAGE, SHIFT_END_MILEAGE, RECEIPT, CAR_DAMAGE, STATEMENT}

    @OneToMany(mappedBy = "imagesType", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Image> images = new ArrayList<>();
}
