package pl.whitedrillv1.domain.crud;

import pl.whitedrillv1.domain.crud.dto.AddressDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentBasicUpdateDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentRequestDto;
import pl.whitedrillv1.domain.crud.dto.PatientGenderDto;
import pl.whitedrillv1.domain.crud.dto.PatientRequestDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleRequestDto;

import java.time.LocalDate;
import java.time.LocalTime;

public interface TestDataFactory {

    default ScheduleRequestDto createDefaultScheduleRequestDto() {
        return new ScheduleRequestDto(
                LocalDate.of(2025, 5, 20),
                LocalTime.of(9, 0),
                LocalTime.of(18, 0)
        );
    }

    default AppointmentRequestDto createDefaultAppointmentRequestDto(Long patientId, Long dentistId, LocalDate date, LocalTime time, int duration) {
        return AppointmentRequestDto.builder()
                .appointmentDate(date)
                .appointmentTime(time)
                .duration(duration)
                .patientId(patientId)
                .dentistId(dentistId)
                .build();
    }

    default AppointmentBasicUpdateDto createAppointmentBasicUpdateDto(LocalTime newTime, int newDuration) {
        return AppointmentBasicUpdateDto.builder()
                .appointmentTime(newTime)
                .duration(newDuration)
                .build();
    }
}
