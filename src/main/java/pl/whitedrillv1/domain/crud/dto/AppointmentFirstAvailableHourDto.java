package pl.whitedrillv1.domain.crud.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentFirstAvailableHourDto(
        LocalDate date,
        LocalTime startHour
) {
}
