package pl.whitedrillv1.domain.crud;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AppointmentRetriever {

    private final AppointmentRepository appointmentRepository;

    Appointment findById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Wizyta o podanym id: " + appointmentId + " nie istanieje."));
    }
}
