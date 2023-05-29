package com.ciosmak.automotivepartner.image;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image_types")
public class ImageType
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;// {SHIFT_START, SHIFT_END, SHIFT_START_MILEAGE, SHIFT_END_MILEAGE, RECEIPT, CAR_DAMAGE, STATEMENT}

    @OneToMany(mappedBy = "imagesType", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Image> images = new ArrayList<>();
}
