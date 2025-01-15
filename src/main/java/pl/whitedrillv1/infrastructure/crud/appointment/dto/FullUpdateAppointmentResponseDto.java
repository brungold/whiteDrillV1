package pl.whitedrillv1.infrastructure.crud.appointment.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record FullUpdateAppointmentResponseDto(
        LocalDate appointmentDate,
        LocalTime appointmentTime,
        Integer duration,
        Long patientId,
        Long dentistId,
        BigDecimal price,
        String appointmentNotes
) {
}
