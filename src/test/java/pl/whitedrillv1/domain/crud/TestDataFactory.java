package pl.whitedrillv1.domain.crud;

import pl.whitedrillv1.domain.crud.dto.AddressDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentBasicUpdateDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentRequestDto;
import pl.whitedrillv1.domain.crud.dto.PatientGenderDto;
import pl.whitedrillv1.domain.crud.dto.PatientRequestDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleRequestDto;

import java.time.LocalDate;
import java.time.LocalTime;

public interface TestDataFactory {

    default ScheduleRequestDto createDefaultScheduleRequestDtoNo1() {
        return new ScheduleRequestDto(
                LocalDate.of(2025, 5, 20),
                LocalTime.of(12, 0),
                LocalTime.of(18, 0)
        );
    }

    default ScheduleRequestDto createDefaultScheduleRequestDtoNo2() {
        return new ScheduleRequestDto(
                LocalDate.of(2025, 5, 21),
                LocalTime.of(11, 0),
                LocalTime.of(19, 0)
        );
    }

    default ScheduleRequestDto createDefaultScheduleRequestDtoNo3() {
        return new ScheduleRequestDto(
                LocalDate.of(2025, 5, 22),
                LocalTime.of(10, 0),
                LocalTime.of(18, 0)
        );
    }

    default AppointmentBasicUpdateDto createAppointmentBasicUpdateDto(LocalTime newTime, int newDuration) {
        return AppointmentBasicUpdateDto.builder()
                .appointmentTime(newTime)
                .duration(newDuration)
                .build();
    }
}
