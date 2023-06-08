package com.ciosmak.automotivepartner.email.repository;

import com.ciosmak.automotivepartner.email.domain.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long>
{
    Optional<Email> findByEmail(String email);
}
