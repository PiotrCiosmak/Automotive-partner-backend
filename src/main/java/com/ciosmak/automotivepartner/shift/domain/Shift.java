package com.ciosmak.automotivepartner.shift.domain;

import com.ciosmak.automotivepartner.accident.domain.Accident;
import com.ciosmak.automotivepartner.car.domain.Car;
import com.ciosmak.automotivepartner.photo.domain.Photo;
import com.ciosmak.automotivepartner.shared.entity.AbstractEntity;
import com.ciosmak.automotivepartner.shift.support.Type;
import com.ciosmak.automotivepartner.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
    public Shift(LocalDate date, Type type, Integer startMileage, BigDecimal lpg, BigDecimal petrol, Integer endMileage, Boolean isStarted, Boolean isDone, Car car, User user)
    {
        this.date = date;
        this.type = type;
        this.startMileage = startMileage;
        this.lpg = lpg;
        this.petrol = petrol;
        this.endMileage = endMileage;
        this.isStarted = isStarted;
        this.isDone = isDone;
        this.car = car;
        this.user = user;
    }

    @ToString.Include
    @Column(name = "date", columnDefinition = "DATE", nullable = false)
    private LocalDate date;

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

    @Column(name = "is_started", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isStarted;

    @Column(name = "is_done", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDone;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "shift", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @Builder.Default
    private List<Accident> accidents = new ArrayList<>();

    @OneToMany(mappedBy = "shift", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @Builder.Default
    private List<Photo> photos = new ArrayList<>();
}
