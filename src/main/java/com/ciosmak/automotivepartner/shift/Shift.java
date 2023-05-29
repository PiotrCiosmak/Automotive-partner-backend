package com.ciosmak.automotivepartner.shift;

import com.ciosmak.automotivepartner.accident.Accident;
import com.ciosmak.automotivepartner.availability.Type;
import com.ciosmak.automotivepartner.entity.AbstractEntity;
import com.ciosmak.automotivepartner.image.Image;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "shifts")
public class Shift extends AbstractEntity
{
    @ToString.Include
    @Column(name = "date", columnDefinition = "DATE", nullable = false)
    private Date date;

    @ToString.Include
    @Column(name = "type", nullable = false)
    private Type type;

    @ToString.Include
    @Column(name = "start_mileage")
    private Integer startMileage;

    @Column(name = "lpg", scale = 2)
    private BigDecimal lpg;

    @Column(name = "petrol", scale = 2)
    private BigDecimal petrol;

    @Column(name = "end_mileage")
    private Integer endMileage;

    @Column(name = "done", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean done;

    @OneToMany(mappedBy = "shift", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Accident> accidents = new ArrayList<>();

    @OneToMany(mappedBy = "shift", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Image> images = new ArrayList<>();
}
