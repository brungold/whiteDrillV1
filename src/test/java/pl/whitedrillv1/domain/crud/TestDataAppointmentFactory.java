package pl.whitedrillv1.domain.crud;

import pl.whitedrillv1.domain.crud.dto.AppointmentRequestDto;

import java.time.LocalDate;
import java.time.LocalTime;

public interface TestDataAppointmentFactory {
    default AppointmentRequestDto createDefaultAppointmentRequestDto(Long patientId, Long dentistId, LocalDate date, LocalTime time, int duration) {
        return AppointmentRequestDto.builder()
                .appointmentDate(date)
                .appointmentTime(time)
                .duration(duration)
                .patientId(patientId)
                .dentistId(dentistId)
                .build();
    }
}
