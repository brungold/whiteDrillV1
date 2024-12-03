package pl.whitedrillv1.domain.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@Transactional
@AllArgsConstructor
class ScheduleUpdater {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleRetriever scheduleRetriever;
    private final AppointmentRetriever appointmentRetriever;

    public void addAppointmentToSchedule(final Long ScheduleId,final Long AppointmentId) {
        Schedule schedule = scheduleRetriever.findScheduleById(ScheduleId);
        Appointment appointment = appointmentRetriever.findAppointmentById(AppointmentId);
        schedule.addAppointment(appointment);
    }


    public void addAppointmentAndBookedHoursToSchedule(final Long id, final Long appointmentId) {
        Schedule schedule = scheduleRetriever.findScheduleById(id);
        Appointment appointment = appointmentRetriever.findAppointmentById(appointmentId);

        schedule.addAppointment(appointment);
        schedule.addReservedHoursFromAppointment(appointment);

        log.info("Schedule updated by adding appointment with id: " + appointmentId
                + " and appointment booked hours: " + appointment.getReservedHours());
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
