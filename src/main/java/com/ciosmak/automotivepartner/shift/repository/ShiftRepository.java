package com.ciosmak.automotivepartner.shift.repository;

import com.ciosmak.automotivepartner.shift.domain.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long>
{
}
