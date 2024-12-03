package pl.whitedrillv1.domain.crud.dto;

import java.time.LocalDate;
import java.util.TreeSet;

public record ScheduleAvailableHoursDto(LocalDate date, TreeSet<Integer> availableHours) {
}
