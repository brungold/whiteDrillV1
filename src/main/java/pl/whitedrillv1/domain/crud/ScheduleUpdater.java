package pl.whitedrillv1.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
class ScheduleUpdater {
    private final ScheduleRepository scheduleRepository;

    public void updateBookedHoursInSchedule(Long id, List<Integer> newBookedHours) {

    }

}
