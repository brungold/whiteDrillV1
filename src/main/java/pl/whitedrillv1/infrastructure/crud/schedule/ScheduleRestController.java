package pl.whitedrillv1.infrastructure.crud.schedule;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.whitedrillv1.domain.crud.ClinicCrudFacade;
import pl.whitedrillv1.domain.crud.dto.ScheduleDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleRequestDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleResponseDto;
import pl.whitedrillv1.infrastructure.crud.schedule.dto.CreatedScheduleResponseDto;
import pl.whitedrillv1.infrastructure.crud.schedule.dto.GetAllSchedulesResponseDto;
import pl.whitedrillv1.infrastructure.crud.schedule.dto.GetScheduleResponseDto;

import java.util.List;

import static pl.whitedrillv1.infrastructure.crud.schedule.ScheduleControllerMapper.*;

@Log4j2
@RestController
@RequestMapping("/schedule")
@AllArgsConstructor
public class ScheduleRestController {

    private final ClinicCrudFacade clinicCrudFacade;

    @GetMapping
    ResponseEntity<GetAllSchedulesResponseDto> getAllSchedules(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        log.info("Get all schedules");
        List<ScheduleDto> allSchedules = clinicCrudFacade.findAllSchedules(pageable);
        GetAllSchedulesResponseDto response = mapFromScheduleDtosToGetAllSchedulesResponseDtos(allSchedules);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<GetScheduleResponseDto> getScheduleById(@PathVariable Long id) {
        log.info("Get schedule by id: " + id);
        ScheduleDto schedule = clinicCrudFacade.findScheduleDtoById(id);
        GetScheduleResponseDto response = mapFromScheduleDtoToGetScheduleResponseDto(schedule);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    ResponseEntity<CreatedScheduleResponseDto> postSchedule(@RequestBody @Valid ScheduleRequestDto scheduleRequestDto) {
        ScheduleResponseDto savedSchedule = clinicCrudFacade.addSchedule(scheduleRequestDto);
        CreatedScheduleResponseDto response = mapFromScheduleRequestDtoToCreatedScheduleResponseDto(savedSchedule);
        log.info("Post schedule: " + response);
        return ResponseEntity.ok(response);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<ScheduleDto> updateSchedule(@PathVariable Long id, @RequestBody @Valid UpdateScheduleRequestDto scheduleRequestDto) {
//        ScheduleDto newScheduleDto =
//    }
}
