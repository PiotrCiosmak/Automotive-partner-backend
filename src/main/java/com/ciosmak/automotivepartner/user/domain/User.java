package com.ciosmak.automotivepartner.user.domain;

import com.ciosmak.automotivepartner.availability.domain.Availability;
import com.ciosmak.automotivepartner.settlement.domain.Settlement;
import com.ciosmak.automotivepartner.shared.entity.AbstractEntity;
import com.ciosmak.automotivepartner.shift.domain.Shift;
import com.ciosmak.automotivepartner.statistic.domain.Statistics;
import com.ciosmak.automotivepartner.user.support.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "users")
public class User extends AbstractEntity implements UserDetails
{
    public User(String firstName, String lastName, String email, String password, String phoneNumber, Role role, boolean isEnabled, boolean isBlocked)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.isEnabled = isEnabled;
        this.isBlocked = isBlocked;
    }

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

    @Column(name = "phone_number", length = 13, nullable = false)
    private String phoneNumber;

    @Column(name = "role", nullable = false)
    private Role role;

    @Builder.Default
    @Column(name = "is_enabled", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isEnabled = false;

    @Builder.Default
    @Column(name = "is_blocked", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isBlocked = false;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Availability> availabilities = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Settlement> settlements = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Statistics> statistics = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Shift> shifts = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public String getUsername()
    {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return !isBlocked;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return isEnabled;
    }
}
