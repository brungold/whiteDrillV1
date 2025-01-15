package pl.whitedrillv1.infrastructure.crud.appointment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record BasicUpdateAppointmentRequestDto(
        LocalDate appointmentDate,
        LocalTime appointmentTime,
        Integer duration,
        BigDecimal price
) {
}
