package com.ciosmak.automotivepartner.user.repository;

import com.ciosmak.automotivepartner.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByEmail(String email);

    List<User> findAllByBlocked(Boolean isBlocked);

    List<User> findAllByRole(String role);

    @Query("SELECT u.blocked FROM User u WHERE u.id = :id")
    boolean isBlocked(@Param("id") Long id);

    @Modifying
    @Query("UPDATE User u SET u.blocked = :isBlocked WHERE u.id = :id")
    void setBlocked(@Param("id") Long id, Boolean isBlocked);

    @Query("SELECT u.role FROM User u WHERE u.id = :id")
    String getRole(@Param("id") Long id);

    @Modifying
    @Query("UPDATE User u SET u.role = :role WHERE u.id = :id")
    void setRole(@Param("id") Long id, String role);
}
