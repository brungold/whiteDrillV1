package pl.whitedrillv1.domain.crud;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@Transactional
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AddressDeleter {

    private final AddressRepository addressRepository;
    private final PatientRepository patientRepository;

    void deleteAddress(Long patientId) {
        // Pobierz pacjenta na podstawie ID
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Pacjent o podanym ID " + patientId + " nie istnieje"));

        // Sprawdzam, czy pacjent ma przypisany adres
        Address address = patient.getAddress();
        if (address == null) {
            log.warn("Pacjent o ID {} nie ma przypisanego adresu.", patientId);
            return; // Nie ma co usuwać
        }

        // Usuń powiązanie adresu z pacjentem
        patient.setAddress(null);
        patientRepository.save(patient);

        // Usuń adres z bazy danych
        addressRepository.deleteById(address.getId());

        log.info("Adres o ID {} należący do pacjenta o ID {} został usunięty.", address.getId(), patientId);
    }
}