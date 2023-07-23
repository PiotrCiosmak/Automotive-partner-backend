package com.ciosmak.automotivepartner.accident.service;

import com.ciosmak.automotivepartner.accident.api.request.AccidentRequest;
import com.ciosmak.automotivepartner.accident.api.resposne.AccidentResponse;
import com.ciosmak.automotivepartner.accident.api.resposne.AdvancedAccidentResponse;
import com.ciosmak.automotivepartner.accident.api.resposne.BaseAccidentResponse;
import com.ciosmak.automotivepartner.accident.domain.Accident;
import com.ciosmak.automotivepartner.accident.repository.AccidentRepository;
import com.ciosmak.automotivepartner.accident.support.AccidentExceptionSupplier;
import com.ciosmak.automotivepartner.accident.support.AccidentMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AccidentService
{
    private final AccidentRepository accidentRepository;
    private final AccidentMapper accidentMapper;

    public AccidentResponse report(AccidentRequest accidentRequest)
    {
        accidentRepository.findByShiftId(accidentRequest.getShiftId()).orElseThrow(AccidentExceptionSupplier.accidentAllReadyReported(accidentRequest.getShiftId()));
        Accident accident = accidentRepository.save(accidentMapper.toAccident(accidentRequest));
        return accidentMapper.toAccidentResponse(accident);
    }

    public List<BaseAccidentResponse> findAllAccidents()
    {
        return accidentRepository.findAll().stream().map(accidentMapper::toBaseAccidentResponse).collect(Collectors.toList());
    }

    public AdvancedAccidentResponse findAccident(Long id)
    {
        Accident accident = accidentRepository.findById(id).orElseThrow(AccidentExceptionSupplier.accidentNotFound(id));
        return accidentMapper.toAdvanceAccidentResponse(accident);
    }
}
