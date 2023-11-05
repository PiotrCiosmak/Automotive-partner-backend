package com.ciosmak.automotivepartner.accident.repository;

import com.ciosmak.automotivepartner.accident.domain.Accident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccidentRepository extends JpaRepository<Accident, Long>
{
    Optional<Accident> findByShiftId(Long shiftId);

    @Query("SELECT COUNT(a) FROM Accident a WHERE a.shift.user.id = :userId AND a.isGuilty = true")
    Integer countGuiltyAccidentsByUserId(@Param("userId") Long userId);
}
