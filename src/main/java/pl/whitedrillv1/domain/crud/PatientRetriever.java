package pl.whitedrillv1.domain.crud;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.whitedrillv1.domain.crud.dto.AddressDto;
import pl.whitedrillv1.domain.crud.dto.PatientDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class PatientRetriever {

    private final PatientRepository patientRepository;

    Set<PatientDto> findAll() {
        log.info("Retrieving all patients");
        return patientRepository.findAll()
                .stream()
                .map(PatientMapper::mapFromPatientToPatientDto)
                .collect(Collectors.toSet());
    }

    Patient findPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Pacjent z podanym id: " + id + ", nie został znaleziony."));
    }

    PatientDto findPatientDtoById(Long id) {
        return patientRepository.findById(id)
                .map(PatientMapper::mapFromPatientToPatientDto)
                .orElseThrow(() -> new PatientNotFoundException("Pacjent z podanym id: " + id + ", nie został znaleziony."));
    }
}
