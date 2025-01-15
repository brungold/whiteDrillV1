package pl.whitedrillv1.infrastructure.crud.appointment.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record GetFirstAvailableHoursResponseDto(
        LocalDate date,
        LocalTime startHour
) {
}
