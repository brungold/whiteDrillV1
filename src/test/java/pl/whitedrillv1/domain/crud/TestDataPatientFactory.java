package pl.whitedrillv1.domain.crud;

import pl.whitedrillv1.domain.crud.dto.AddressDto;
import pl.whitedrillv1.domain.crud.dto.PatientGenderDto;
import pl.whitedrillv1.domain.crud.dto.PatientRequestDto;

import java.time.LocalDate;

interface TestDataPatientFactory {

    default PatientRequestDto createDefaultPatientRequestDtoNo1() {
        return PatientRequestDto.builder()
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1980, 5, 15))
                .phone("1234567890")
                .email("john.doe@example.com")
                .patientGender(PatientGenderDto.MALE)
                .pesel("80051512345")
                .language("English")
                .nationality("American")
                .nip("1234567890")
                .addressDto(AddressDto.builder()
                        .postalCode("12-345")
                        .city("New York")
                        .street("Broadway")
                        .houseNumber("1")
                        .apartmentNumber("101")
                        .build())
                .build();
    }

    default PatientRequestDto createDefaultPatientRequestDtoNo2() {
        return PatientRequestDto.builder()
                .firstName("Daisy")
                .lastName("Doe")
                .birthDate(LocalDate.of(1990, 5, 25))
                .phone("1234567899")
                .email("daisy.doe@example.com")
                .patientGender(PatientGenderDto.FEMALE)
                .pesel("90052513345")
                .language("English")
                .nationality("American")
                .nip("9876543210")
                .addressDto(AddressDto.builder()
                        .postalCode("12-345")
                        .city("New York")
                        .street("Broadway")
                        .houseNumber("1")
                        .apartmentNumber("102")
                        .build())
                .build();
    }
}
