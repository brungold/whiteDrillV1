package pl.whitedrillv1.domain.crud;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.whitedrillv1.domain.crud.dto.AppointmentDto;

@Service
@Log4j2
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AppointmentRetriever {

    private final AppointmentRepository appointmentRepository;


    Appointment findAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Wizyta o podanym id: " + appointmentId + " nie istanieje."));
    }
    AppointmentDto findAppointmentDtoById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .map(AppointmentMapper::mapFromAppointmentToAppointmentDto)
                .orElseThrow(() -> new AppointmentNotFoundException("Wizyta o podanym id: " + appointmentId + " nie istanieje."));
    }

    public boolean hasUpcomingAppointments(Long patientId) {
        return appointmentRepository.existsByPatientIdAndStatusFromToday(
                patientId, AppointmentStatus.SCHEDULED);
    }
}
