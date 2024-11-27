package pl.whitedrillv1.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.whitedrillv1.domain.crud.dto.ScheduleDto;

import java.time.LocalDate;

@Log4j2
@Service
@AllArgsConstructor
class ScheduleRetriever {

    private final ScheduleRepository scheduleRepository;

    ScheduleDto findScheduleByDate(LocalDate date) {
        return scheduleRepository.findByDate(date)
                .map(ScheduleMapper::mapFromScheduleToScheduleDto)
                .orElseThrow(() -> new ScheduleNotFoundException("Grafik na ten dzień: " + date + " jeszcze nie istnieje," +
                        " jeżeli tego dnia odbyć się wizyta musisz utworzyć grafik"));
    }

    void scheduleExists(LocalDate date) {
        if (scheduleRepository.existsByDate(date)) {
            throw new ScheduleAlreadyExistsException("Dla tej daty " + date + " jest już utworzony grafik pracy.");
        }
    }
}
