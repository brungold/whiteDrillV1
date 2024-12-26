package pl.whitedrillv1.domain.crud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.whitedrillv1.domain.crud.DentistRetriever;

@Service
@AllArgsConstructor
class DentistAssigner {

    private final DentistRetriever dentistRetriever;
    private final AppointmentRetriever appointmentRetriever;

    void assignDentistOskarToAppointment(Long appointmentId) {
        Appointment appointment = appointmentRetriever.findAppointmentById(appointmentId);
        Dentist dentist = dentistRetriever.findDentistById(1L);
        appointment.setDentist(dentist);
    }

    public Schedule assignDefaultDentistOskarToSchedule(Schedule schedule) {
        Dentist defaultDentist = dentistRetriever.findDentistById(1L);
        schedule.setDentist(defaultDentist);

        return schedule;
    }

    public Dentist getDefaultOskarDentist() {
        return dentistRetriever.findDentistById(1L);
    }
}
