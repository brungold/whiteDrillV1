package pl.whitedrillv1.domain.crud.dto;

import java.time.LocalTime;

public record AppointmentDtoForSchedule(
        Long id,
        LocalTime appointmentTime,
        Integer duration,
        String status,
        Long patientId
) {
}
