package com.ciosmak.automotivepartner.availability;

import com.ciosmak.automotivepartner.user.User;
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
@Table(name = "availability")
public class Availability
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private State state;

    private Date date;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "availability_users", joinColumns = {@JoinColumn(name = "availability_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> users = new ArrayList<>();
}
