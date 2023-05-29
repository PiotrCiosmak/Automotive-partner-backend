package com.ciosmak.automotivepartner.shift;

import com.ciosmak.automotivepartner.accident.Accident;
import com.ciosmak.automotivepartner.availability.Type;
import com.ciosmak.automotivepartner.image.Image;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shifts")
public class Shift
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    private Type type;

    private Integer startMileage;

    private Double lpg;

    private Double petrol;

    private Integer endMileage;

    private Boolean done;//DEFAULT FALSE

    @OneToMany(mappedBy = "shifts", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Accident> accidents = new ArrayList<>();

    @OneToMany(mappedBy = "shifts", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Image> images = new ArrayList<>();
}
