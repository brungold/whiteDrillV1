package pl.whitedrillv1.domain.crud;

import lombok.extern.log4j.Log4j2;

import pl.whitedrillv1.domain.crud.dto.DentistDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleResponseDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleRequestDto;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;

import static pl.whitedrillv1.domain.crud.DentistMapper.mapFromDentistToDentistDto;

@Log4j2
@Service
@AllArgsConstructor
class ScheduleAdder {

    private final DentistAssigner dentistAssigner;
    private final ScheduleRetriever scheduleRetriever;
    private final ScheduleRepository scheduleRepository;

    ScheduleResponseDto addSchedule(final ScheduleRequestDto request) {
        validateScheduleTimesRequest(request);
        validateScheduleUniqueness(request.date());

        Schedule schedule = Schedule.builder()
                .date(request.date())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .appointments(new HashSet<>()) // <- Inicjalizacja pustego zbioru
                .bookedHours(new HashSet<>()) // <- Inicjalizacja pustego zbioru
                .build();

        schedule = dentistAssigner.assignDefaultDentistOskarToSchedule(schedule);
        Dentist dentist = schedule.getDentist();
        DentistDto dentistDto = mapFromDentistToDentistDto(dentist);

        log.info("Adding new schedule: {}", schedule);

        Schedule savedSchedule = scheduleRepository.save(schedule);
        /*
        !dopóki jest jeden dentysta wydajniej jest go przypisywać!
         */
        return new ScheduleResponseDto(savedSchedule.getId(), savedSchedule.getDate(),
                savedSchedule.getStartTime(), savedSchedule.getEndTime(),dentistDto); // <- dentostDto, shame
    }


    private void validateScheduleTimesRequest(ScheduleRequestDto request) {
        if (!request.startTime().isBefore(request.endTime())) {
            throw new ScheduleInvalidTimeRangeException("Godzina rozpoczęcia dnia pracy, nie może być późniejsza niż godzina końca dnia pracy.");
        }
    }

    private void validateScheduleUniqueness(LocalDate date) {
        scheduleRetriever.scheduleExists(date);
    }
}
