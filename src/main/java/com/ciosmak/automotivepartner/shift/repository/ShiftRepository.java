package com.ciosmak.automotivepartner.shift.repository;

import com.ciosmak.automotivepartner.shift.support.Type;
import com.ciosmak.automotivepartner.shift.domain.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long>
{
    List<Shift> findAllByDate(LocalDate date);

    Optional<Shift> findByUser_IdAndDateAndType(Long userId, LocalDate date, Type type);

    List<Shift> findAllByDateAndType(LocalDate date, Type type);
}
