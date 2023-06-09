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

    List<User> findAllByBlockedFalse();

    List<User> findAllByBlockedTrue();

    List<User> findAllByRole(String role);

    @Query("SELECT u.blocked FROM User u WHERE u.id = :id")
    boolean isBlocked(@Param("id") Long id);
//TODO zmienić na jedno setBlocked z parametrem w którym jest true/false
    @Modifying
    @Query("UPDATE User u SET u.blocked = true WHERE u.id = :id")
    void setBlockedTrue(@Param("id") Long id);

    @Modifying
    @Query("UPDATE User u SET u.blocked = false WHERE u.id = :id")
    void setBlockedFalse(@Param("id") Long id);

    @Query("SELECT u.role FROM User u WHERE u.id = :id")
    boolean isAdmin(@Param("id") Long id);

    @Modifying
    @Query("UPDATE User u SET u.role = :role WHERE u.id = :id")
    void setRole(@Param("id") Long id, @Param("role") String role);
}
