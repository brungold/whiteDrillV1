package pl.whitedrillv1.domain.crud.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

/*
Chce znaleźć eleganckie rozwiązanie które będzie pomagać z dentystą i czasem
 */

@Builder
public record ScheduleRequestDto(
        @NotNull(message = "Date cannot be null")
        @FutureOrPresent(message = "Date must be in the present or future")
        LocalDate date,

        @NotNull(message = "Start time cannot be null")
        LocalTime startTime,

        @NotNull(message = "End time cannot be null")
        LocalTime endTime
) {
    public ScheduleRequestDto {
        if (startTime != null && endTime != null && !startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
    }
}
