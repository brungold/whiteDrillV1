package pl.whitedrillv1.domain.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.whitedrillv1.domain.crud.Schedule;
import pl.whitedrillv1.domain.crud.ScheduleRepository;
import pl.whitedrillv1.domain.crud.ScheduleRetriever;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
class AppointmentDeleter {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentRetriever appointmentRetriever;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleRetriever scheduleRetriever;

    public void deleteAppointment(Long appointmentId) {
        // Pobierz wizytę do usunięcia za pomocą AppointmentRetriever
        Appointment appointment = appointmentRetriever.findAppointmentById(appointmentId);

        // Pobierz harmonogram powiązany z wizytą
        Schedule schedule = appointment.getSchedule();

        if (schedule != null) {
            // Usuń wizytę z listy `appointments`
            schedule.getAppointments().remove(appointment);

            // Usuń godziny wizyty z `bookedHours`
            schedule.getBookedHours().removeAll(appointment.getReservedHours());

            // Zapisz zaktualizowany harmonogram
            scheduleRepository.save(schedule);
        }

        // Usuń wizytę z bazy danych
        appointmentRepository.deleteById(appointmentId);
        log.info("Appointment deleted with id: " + appointmentId);
    }
}
