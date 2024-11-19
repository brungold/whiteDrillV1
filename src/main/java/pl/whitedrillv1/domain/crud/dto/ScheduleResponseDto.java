package pl.whitedrillv1.domain.crud.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleResponseDto(
        Long id,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        DentistDto dentistDto
        ) {
}
