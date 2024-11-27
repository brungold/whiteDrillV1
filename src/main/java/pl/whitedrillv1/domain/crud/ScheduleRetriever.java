package pl.whitedrillv1.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.whitedrillv1.domain.crud.dto.ScheduleDto;

import java.time.LocalDate;
import java.util.List;

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
}
