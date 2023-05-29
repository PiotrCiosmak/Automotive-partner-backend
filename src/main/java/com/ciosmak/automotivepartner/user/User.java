package com.ciosmak.automotivepartner.user;

import com.ciosmak.automotivepartner.availability.Availability;
import com.ciosmak.automotivepartner.image.Image;
import com.ciosmak.automotivepartner.settlement.Settlement;
import com.ciosmak.automotivepartner.shift.Shift;
import com.ciosmak.automotivepartner.statistic.Statistic;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @NaturalId(mutable = true)
    private String email;

    private String password;

    private String phoneNumber;

    private String role;

    private boolean enabled = false;//DEFAULT FALSE

    private boolean blocked = false;//DEFAULT FALSE

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Availability> availabilities = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Settlement> settlements = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Statistic> statistics = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Shift> shifts = new ArrayList<>();
}
