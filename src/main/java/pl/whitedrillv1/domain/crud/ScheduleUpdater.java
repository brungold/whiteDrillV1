package pl.whitedrillv1.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.whitedrillv1.domain.crud.dto.ScheduleDto;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
class ScheduleUpdater {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleRetriever scheduleRetriever;
    private final AppointmentRetriever appointmentRetriever;

    public void addAppointmentToSchedule(Long id, Long appointmentId) {

    }

    public void deleteAppointmentToSchedule(Long id, Long appointmentId) {

    }

    /**
     * Zastanowić się jak dodawać/ usuwać listę godzin Appointment z setu godzin Schedule
     * @param id
     * @param newBookedHours
     */
    public void addBookedHoursInSchedule(Long id, List<Integer> newBookedHours) {

    }

    public void deleteBookedHoursInSchedule(Long id, List<Integer> newBookedHours) {

    }
}
