package pl.whitedrillv1.infrastructure.crud.schedule;

import pl.whitedrillv1.domain.crud.dto.ScheduleDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleResponseDto;
import pl.whitedrillv1.infrastructure.crud.schedule.dto.CreatedScheduleResponseDto;
import pl.whitedrillv1.infrastructure.crud.schedule.dto.GetAllSchedulesResponseDto;
import pl.whitedrillv1.infrastructure.crud.schedule.dto.GetScheduleResponseDto;

import java.util.List;

public class ScheduleControllerMapper {

    static GetScheduleResponseDto mapFromScheduleDtoToGetScheduleResponseDto(ScheduleDto scheduleDto) {
        return new GetScheduleResponseDto(scheduleDto);
    }

    static GetAllSchedulesResponseDto mapFromScheduleDtosToGetAllSchedulesResponseDtos(List<ScheduleDto> scheduleDtos) {
        return new GetAllSchedulesResponseDto(scheduleDtos);
    }

    static CreatedScheduleResponseDto mapFromScheduleRequestDtoToCreatedScheduleResponseDto(ScheduleResponseDto scheduleResponseDto) {
        return new CreatedScheduleResponseDto(scheduleResponseDto);
    }
}
