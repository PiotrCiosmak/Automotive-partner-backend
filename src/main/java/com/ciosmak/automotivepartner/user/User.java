package com.ciosmak.automotivepartner.user;

import com.ciosmak.automotivepartner.availability.Availability;
import com.ciosmak.automotivepartner.entity.AbstractEntity;
import com.ciosmak.automotivepartner.settlement.Settlement;
import com.ciosmak.automotivepartner.shift.Shift;
import com.ciosmak.automotivepartner.statistic.Statistics;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "users")
public class User extends AbstractEntity
{
    @ToString.Include
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @ToString.Include
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ToString.Include
    @NaturalId(mutable = true)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", length = 12, nullable = false)
    private String phoneNumber;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "enabled", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean enabled = false;

    @Column(name = "blocked", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean blocked = false;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Availability> availabilities = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Settlement> settlements = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Statistics> statistics = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Shift> shifts = new ArrayList<>();
}
