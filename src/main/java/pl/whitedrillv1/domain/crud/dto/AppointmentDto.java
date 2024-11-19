package pl.whitedrillv1.domain.crud.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/*
 * DTO dla Encji Appointment, rozważyć czy
 * - podawać patientId, imię nazwisko oraz dentystę
 * - podawać DTO pacjenta aby wyświetlał się w całości
 */

@Builder
public record AppointmentDto(
        Long id,
        LocalDate appointmentDate,
        LocalTime appointmentTime,
        int duration,
        BigDecimal price,
        String status,
        String appointmentNotes,
        Long patientId,
        String patientName,
        Long dentistId,
        String dentistName
) {
}
