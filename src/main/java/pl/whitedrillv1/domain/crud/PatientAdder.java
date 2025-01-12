package pl.whitedrillv1.domain.crud;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import pl.whitedrillv1.domain.crud.dto.PatientDto;
import pl.whitedrillv1.domain.crud.dto.PatientRequestDto;

@Log4j2
@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class PatientAdder {

    private final PatientRepository patientRepository;

    PatientDto addPatient(final PatientRequestDto patientRequestDto) {
        // Konwersja płci z PatientGenderDto na PatientGender
        PatientGender patientGender = PatientGender.valueOf(patientRequestDto.patientGender().name());

        // Utworzenie encji Address na podstawie AddressDto
        Address address = null;
        if (patientRequestDto.addressDto() != null) {
            address = Address.builder()
                    .postalCode(patientRequestDto.addressDto().postalCode())
                    .city(patientRequestDto.addressDto().city())
                    .street(patientRequestDto.addressDto().street())
                    .houseNumber(patientRequestDto.addressDto().houseNumber())
                    .apartmentNumber(patientRequestDto.addressDto().apartmentNumber())
                    .build();
        }

        // Utworzenie encji Patient na podstawie PatientRequestDto
        Patient patient = Patient.builder()
                .firstName(patientRequestDto.firstName())
                .lastName(patientRequestDto.lastName())
                .maidenName(patientRequestDto.maidenName())
                .language(patientRequestDto.language())
                .nationality(patientRequestDto.nationality())
                .patientGender(patientGender)
                .birthDate(patientRequestDto.birthDate())
                .pesel(patientRequestDto.pesel())
                .nip(patientRequestDto.nip())
                .phone(patientRequestDto.phone())
                .email(patientRequestDto.email())
                .address(address)
                .build();

        log.info("Dodawanie pacjenta: {}", patient);
        // Zapis pacjenta w bazie danych
        Patient savedPatient = patientRepository.save(patient);

        // Konwersja zapisanej encji Patient do PatientDto
        return PatientMapper.mapFromPatientToPatientDto(savedPatient);
    }
}