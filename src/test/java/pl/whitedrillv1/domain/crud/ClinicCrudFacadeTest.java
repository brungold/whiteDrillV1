package pl.whitedrillv1.domain.crud;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import pl.whitedrillv1.domain.crud.dto.AddressDto;
import pl.whitedrillv1.domain.crud.dto.PatientDto;
import pl.whitedrillv1.domain.crud.dto.PatientGenderDto;
import pl.whitedrillv1.domain.crud.dto.PatientRequestDto;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClinicCrudFacadeTest {

    ClinicCrudFacade clinicCrudFacade = ClinicCrudFacadeConfiguration.createClinicCrudFacade(
            new InMemoryPatientRepository()
    );

    @Test
    @DisplayName("Should add patient john test with id: 0 when john was sent")
    public void should_add_patient_john_test_with_id_zero_when_john_was_sent() {
        //given
        PatientRequestDto patient = PatientRequestDto.builder()
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1980, 5, 15))
                .phone("1234567890")
                .email("john.doe@example.com")
                .patientGender(PatientGenderDto.MALE)
                .pesel("80051512345")
                //.maidenName("Smith")
                .language("English")
                .nationality("American")
                .nip("1234567890")
                .addressDto(AddressDto.builder()
                        .postalCode(12345)
                        .city("New York")
                        .street("Broadway")
                        .houseNumber(1)
                        .apartmentNumber(101)
                        .build())
                .build();

        // when
        PatientDto result = clinicCrudFacade.addPatient(patient);

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(0L);
        assertThat(result.firstName()).isEqualTo("John");
        assertThat(result.lastName()).isEqualTo("Doe");
        assertThat(result.gender()).isEqualTo("MALE");
        assertThat(result.pesel()).isEqualTo("80051512345");
        assertThat(result.email()).isEqualTo("john.doe@example.com");
        assertThat(result.address().city()).isEqualTo("New York");
    }

    @Test
    @DisplayName("Should retrieve patient by id")
    public void should_retrieve_patient_by_id() {
        //given
        PatientRequestDto patient = PatientRequestDto.builder()
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1980, 5, 15))
                .phone("1234567890")
                .email("john.doe@example.com")
                .patientGender(PatientGenderDto.MALE)
                .pesel("80051512345")
                //.maidenName("Smith")
                .language("English")
                .nationality("American")
                .nip("1234567890")
                .addressDto(AddressDto.builder()
                        .postalCode(12345)
                        .city("New York")
                        .street("Broadway")
                        .houseNumber(1)
                        .apartmentNumber(101)
                        .build())
                .build();
        PatientDto addedPatient = clinicCrudFacade.addPatient(patient);
        assertThat(addedPatient).isNotNull();

        //when
        PatientDto patientDtoById = clinicCrudFacade.findPatientDtoById(addedPatient.id());
        //then
        assertThat(patientDtoById.id()).isEqualTo(0L);
        assertThat(patientDtoById.firstName()).isEqualTo("John");
        assertThat(patientDtoById.lastName()).isEqualTo("Doe");
        assertThat(patientDtoById.gender()).isEqualTo("MALE");
        assertThat(patientDtoById.pesel()).isEqualTo("80051512345");
        assertThat(patientDtoById.email()).isEqualTo("john.doe@example.com");
        assertThat(patientDtoById.address().city()).isEqualTo("New York");
    }

    @Test
    @DisplayName("Should throw exception PatientNotFound When id was: 0")
    public void should_throw_patient_not_found_exception_when_id_was_zero() {
        //given
        //assertThat(clinicCrudFacade.)
        //when

        //then
    }
}