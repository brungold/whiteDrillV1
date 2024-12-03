package pl.whitedrillv1.domain.crud;

import pl.whitedrillv1.domain.crud.dto.ScheduleAvailableHoursDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleDto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.TreeSet;

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

    public static ScheduleAvailableHoursDto mapFromTreeSetWithAvailableHoursToScheduleAvailableHoursDto(
            LocalDate date, TreeSet<Integer> availableHours) {
        return new ScheduleAvailableHoursDto(date, availableHours);
    }
}
