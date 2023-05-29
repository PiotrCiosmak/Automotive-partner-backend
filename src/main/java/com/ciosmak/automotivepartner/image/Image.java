package com.ciosmak.automotivepartner.image;

import com.ciosmak.automotivepartner.entity.AbstractEntity;
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
}
