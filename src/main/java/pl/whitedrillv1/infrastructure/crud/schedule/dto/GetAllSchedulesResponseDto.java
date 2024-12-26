package pl.whitedrillv1.infrastructure.crud.schedule.dto;

import pl.whitedrillv1.domain.crud.dto.ScheduleDto;

import java.util.List;

public record GetAllSchedulesResponseDto(List<ScheduleDto> schedules) {
}
