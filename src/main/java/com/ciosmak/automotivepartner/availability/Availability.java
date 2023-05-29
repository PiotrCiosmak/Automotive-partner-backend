package com.ciosmak.automotivepartner.availability;

import com.ciosmak.automotivepartner.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "availability")
public class Availability extends AbstractEntity
{
    @ToString.Include
    @Column(name = "type", nullable = false)
    private Type type;

    @ToString.Include
    @Column(name = "date", columnDefinition = "DATE", nullable = false)
    private Date date;
}
