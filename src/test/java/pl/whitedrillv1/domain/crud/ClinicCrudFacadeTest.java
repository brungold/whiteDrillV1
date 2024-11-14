package pl.whitedrillv1.domain.crud;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import pl.whitedrillv1.domain.crud.dto.AddressDto;
import pl.whitedrillv1.domain.crud.dto.PatientDto;
import pl.whitedrillv1.domain.crud.dto.PatientGenderDto;
import pl.whitedrillv1.domain.crud.dto.PatientRequestDto;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

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
        assertNotNull(result);
        assertEquals("John", result.firstName());
        assertEquals("Doe", result.lastName());
        assertEquals("MALE", result.gender());
        assertEquals("80051512345", result.pesel());
        assertEquals("john.doe@example.com", result.email());
        assertEquals("New York", result.address().city());
    }
}