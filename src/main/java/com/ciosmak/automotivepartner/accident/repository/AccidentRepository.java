package com.ciosmak.automotivepartner.accident.repository;

import com.ciosmak.automotivepartner.accident.domain.Accident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccidentRepository extends JpaRepository<Accident, Long>
{
    Optional<Accident> findByShiftId(Long shiftId);
}
