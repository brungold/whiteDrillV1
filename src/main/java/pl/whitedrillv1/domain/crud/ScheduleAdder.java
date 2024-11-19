package pl.whitedrillv1.domain.crud;

import lombok.extern.log4j.Log4j2;
import pl.whitedrillv1.domain.crud.dto.DentistDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleResponseDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleRequestDto;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static pl.whitedrillv1.domain.crud.DentistMapper.mapFromDentistToDentistDto;

@Log4j2
@Service
@AllArgsConstructor
class ScheduleAdder {

    private final DentistAssigner dentistAssigner;
    private final ScheduleRepository scheduleRepository;

    ScheduleResponseDto addSchedule(final ScheduleRequestDto request) {
        validateScheduleRequest(request);

        Schedule schedule = Schedule.builder()
                .date(request.date())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .build();

        schedule = dentistAssigner.assignDefaultDentistOskarToSchedule(schedule);
        Dentist dentist = schedule.getDentist();
        DentistDto dentistDto = mapFromDentistToDentistDto(dentist);

        log.info("Adding new schedule: {}", schedule);

        Schedule savedSchedule = scheduleRepository.save(schedule);
        /*TODO
        !Zwróć uwagę że dentistDto nie powinien się tutaj brać z czapki, do refactoru!
         */
        return new ScheduleResponseDto(savedSchedule.getId(), savedSchedule.getDate(),
                savedSchedule.getStartTime(), savedSchedule.getEndTime(),dentistDto); // <- dentostDto, shame
    }


    private void validateScheduleRequest(ScheduleRequestDto request) {
        if (!request.startTime().isBefore(request.endTime())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
    }
}
