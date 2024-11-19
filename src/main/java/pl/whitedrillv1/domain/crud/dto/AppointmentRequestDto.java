package pl.whitedrillv1.domain.crud.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record AppointmentRequestDto(
        @NotNull(message = "Appointment date is required")
        LocalDate appointmentDate,

        @NotNull(message = "Appointment time is required")
        LocalTime appointmentTime,

        @Positive(message = "Duration must be greater than 0")
        int duration,

        BigDecimal price,

        @NotNull(message = "Status is required")
        String status,

        String appointmentNotes,

        @NotNull(message = "Patient ID is required")
        Long patientId,

        @NotNull(message = "Dentist ID is required")
        Long dentistId
) {
}
