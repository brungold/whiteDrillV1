package pl.whitedrillv1.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.whitedrillv1.domain.crud.dto.ScheduleAvailableHoursDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log4j2
@Service
@AllArgsConstructor
class ScheduleRetriever {

    private final ScheduleRepository scheduleRepository;

    List<ScheduleDto> findAllSchedules(Pageable pageable) {
        log.info("Retrieving all schedules: ");
        return scheduleRepository.findAll(pageable)
                .stream()
                .map(ScheduleMapper::mapFromScheduleToScheduleDto)
                .toList();
    }

    ScheduleDto findScheduleDtoByDate(LocalDate date) {
        return scheduleRepository.findByDate(date)
                .map(ScheduleMapper::mapFromScheduleToScheduleDto)
                .orElseThrow(() -> new ScheduleNotFoundException("Grafik na ten dzień: " + date + " jeszcze nie istnieje," +
                        " jeżeli tego dnia odbyć się wizyta musisz utworzyć grafik"));
    }

    ScheduleDto findScheduleDtoById(Long id) {
        return scheduleRepository.findById(id)
                .map(ScheduleMapper::mapFromScheduleToScheduleDto)
                .orElseThrow(() -> new ScheduleNotFoundException("Grafik na ten dzień z Id równe: " + id + " jeszcze nie istnieje," +
                        " jeżeli tego dnia odbyć się wizyta musisz utworzyć grafik"));
    }

    Schedule findScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("Grafik na ten dzień z Id równe: " + id + " jeszcze nie istnieje," +
                        " jeżeli tego dnia odbyć się wizyta musisz utworzyć grafik"));
    }

    Schedule findScheduleByDate(LocalDate date) {
        return scheduleRepository.findByDate(date)
                .orElseThrow(() -> new ScheduleNotFoundException("Grafik na ten dzień: " + date + " jeszcze nie istnieje," +
                        " jeżeli tego dnia odbyć się wizyta musisz utworzyć grafik"));
    }

    void scheduleExists(LocalDate date) {
        if (scheduleRepository.existsByDate(date)) {
            throw new ScheduleAlreadyExistsException("Dla tej daty " + date + " jest już utworzony grafik pracy.");
        }
    }

    public ScheduleAvailableHoursDto findAvailableHoursByDate(LocalDate date) {
        Schedule schedule = findScheduleByDate(date);

        // Obliczam zakres godzin pracy
        Set<Integer> allHours = IntStream.range(schedule.getStartTime().getHour(), schedule.getEndTime().getHour())
                .boxed()
                .collect(Collectors.toSet());

        // Obliczam wolne godziny jako różnicę
        TreeSet<Integer> availableHours = allHours.stream()
                .filter(hour -> !schedule.getBookedHours().contains(hour))
                .collect(Collectors.toCollection(TreeSet::new));

        log.info("Available hours for schedule on {}: {}", date, availableHours);

        // Mapuje TreeSet na ScheduleAvailableHoursDto
        return ScheduleMapper.mapFromTreeSetWithAvailableHoursToScheduleAvailableHoursDto(date, availableHours);
    }

    public List<Schedule> findSchedulesByDentistIdOrderedByDate(Long dentistId) {
        return scheduleRepository.findAllByDentistIdOrderByDateAsc(dentistId);
    }
}
