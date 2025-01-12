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

    default PatientRequestDto createDefaultPatientRequestDtoNo3() {
        return PatientRequestDto.builder()
                .firstName("Michael")
                .lastName("Smith")
                .birthDate(LocalDate.of(1975, 7, 12))
                .phone("9876543210")
                .email("michael.smith@example.com")
                .patientGender(PatientGenderDto.MALE)
                .pesel("75071212345")
                .language("Polish")
                .nationality("Polish")
                .nip("4567890123")
                .addressDto(AddressDto.builder()
                        .postalCode("00-123")
                        .city("Warsaw")
                        .street("Marszalkowska")
                        .houseNumber("15")
                        .apartmentNumber("10")
                        .build())
                .build();
    }

    default PatientRequestDto createDefaultPatientRequestDtoNo4() {
        return PatientRequestDto.builder()
                .firstName("Anna")
                .lastName("Kowalska")
                .birthDate(LocalDate.of(1988, 12, 5))
                .phone("5551234567")
                .email("anna.kowalska@example.com")
                .patientGender(PatientGenderDto.FEMALE)
                .pesel("88120554321")
                .language("English")
                .nationality("Canadian")
                .nip("7894561230")
                .addressDto(AddressDto.builder()
                        .postalCode("A1B 2C3")
                        .city("Toronto")
                        .street("Queen Street")
                        .houseNumber("200")
                        .apartmentNumber("5B")
                        .build())
                .build();
    }

    default PatientRequestDto createDefaultPatientRequestDtoWithoutAddress() {
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
                .build();
    }
}
