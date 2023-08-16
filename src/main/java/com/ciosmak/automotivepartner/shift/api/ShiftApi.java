package com.ciosmak.automotivepartner.shift.api;

import com.ciosmak.automotivepartner.shift.service.ShiftService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "shifts")
@AllArgsConstructor
@RestController
@RequestMapping("api/shifts")
public class ShiftApi
{
    private final ShiftService shiftService;

}
