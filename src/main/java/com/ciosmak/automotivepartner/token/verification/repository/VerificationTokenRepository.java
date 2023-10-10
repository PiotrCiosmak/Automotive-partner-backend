package com.ciosmak.automotivepartner.token.verification.repository;

import com.ciosmak.automotivepartner.token.verification.domain.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>
{
    VerificationToken findByToken(String token);
}
