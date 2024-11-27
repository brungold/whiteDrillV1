package pl.whitedrillv1.domain.crud.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Builder
public record ScheduleDto(
        Long id,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        List<AppointmentDto> appointments,
        Set<Integer> bookedHours
) {
}
