package com.ciosmak.automotivepartner.shift.service;

import com.ciosmak.automotivepartner.shift.repository.ShiftRepository;
import com.ciosmak.automotivepartner.shift.support.ShiftMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ShiftService
{
    private final ShiftRepository shiftRepository;
    private final ShiftMapper shiftMapper;
}
