package pl.whitedrillv1.domain.crud;

import pl.whitedrillv1.domain.crud.dto.ScheduleDto;

import java.util.HashSet;

class ScheduleMapper {

    public static ScheduleDto mapFromScheduleToScheduleDto(Schedule schedule) {
        return ScheduleDto.builder()
                .id(schedule.getId())
                .date(schedule.getDate())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .appointments(schedule.getAppointments().stream()
                        .map(AppointmentMapper::mapFromAppointmentToAppointmentDto)
                        .toList()) // Mapowanie listy wizyt
                .bookedHours(new HashSet<>(schedule.getBookedHours())) // Kopiowanie zbioru godzin
                .build();
    }
}
