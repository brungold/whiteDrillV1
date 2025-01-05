package pl.whitedrillv1.domain.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
class PatientDeleter {

    private final PatientRetriever patientRetriever;
    private final PatientRepository patientRepository;
    private final AppointmentRetriever appointmentRetriever;
    private final AddressRepository addressRepository;

    public void deletePatient(Long patientId) {
        // 1. Pobierz pacjenta z bazy
        Patient patient = patientRetriever.findPatientById(patientId);

        // 2. Sprawdź, czy pacjent ma wizyty, które jeszcze się nie odbyły
        boolean hasUpcomingAppointments = appointmentRetriever.hasUpcomingAppointments(patientId);

        if (hasUpcomingAppointments) {
            throw new PatientHasUpcomingAppointmentsException("Pacjent o ID " + patientId + " ma umówione wizyty.");
        }
        // 3. Usuń powiązany adres, jeśli istnieje
        if (patient.getAddress() != null) {
            addressRepository.deleteById(patient.getId());
            log.info("Usunięto adres pacjenta o ID {}", patientId);
        }

        // 4. Usuń pacjenta, ale pozostaw jego wcześniejsze wizyty w bazie
        patientRepository.deleteById(patient.getId());
        log.info("Usunięto pacjenta o ID {}", patientId);
    }
}