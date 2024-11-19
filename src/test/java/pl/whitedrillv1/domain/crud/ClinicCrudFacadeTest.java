package pl.whitedrillv1.domain.crud;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import pl.whitedrillv1.domain.crud.dto.AddressDto;
import pl.whitedrillv1.domain.crud.dto.PatientDto;
import pl.whitedrillv1.domain.crud.dto.PatientGenderDto;
import pl.whitedrillv1.domain.crud.dto.PatientRequestDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleRequestDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleResponseDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ClinicCrudFacadeTest {

    ClinicCrudFacade clinicCrudFacade = ClinicCrudFacadeConfiguration.createClinicCrudFacade(
            new InMemoryPatientRepository(),
            new InMemoryScheduleRepository(),
            new InMemoryDentistRepository(),
            new InMemoryAppointmentRepository()
    );

    /* ==========================
    Section: Patient Entity Tests
    ========================== */
    // TC for -> addPatient
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

    // TC for -> findPatientDtoById
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

    // TC for -> PatientNotFoundException
    @Test
    @DisplayName("Should throw exception PatientNotFound When id was: 0")
    public void should_throw_patient_not_found_exception_when_id_was_zero() {
        //given
        assertThat(clinicCrudFacade.findAllPatients()).isEmpty();
        //when
        Throwable throwable = catchThrowable(() -> clinicCrudFacade.findPatientDtoById(0L));
        //then
        assertThat(throwable).isInstanceOf(PatientNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Pacjent z podanym id: 0, nie został znaleziony.");
    }

    // TC for findAllPatients
    @Test
    @DisplayName("Should retrieve all patients")
    public void should_retrieve_all_patients() {
        //given
        PatientRequestDto patientNo1 = PatientRequestDto.builder()
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
                        .postalCode(12345)
                        .city("New York")
                        .street("Broadway")
                        .houseNumber(1)
                        .apartmentNumber(101)
                        .build())
                .build();

        PatientRequestDto patientNo2 = PatientRequestDto.builder()
                .firstName("Daisy")
                .lastName("Doe")
                .birthDate(LocalDate.of(1990, 5, 25))
                .phone("1234567899")
                .email("john.doe@example.com")
                .patientGender(PatientGenderDto.FEMALE)
                .pesel("90052513345")
                .maidenName("Smith")
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
        clinicCrudFacade.addPatient(patientNo1);
        clinicCrudFacade.addPatient(patientNo2);

        //when
        Set<PatientDto> result = clinicCrudFacade.findAllPatients();

        //then
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .extracting(PatientDto::firstName)
                .containsExactlyInAnyOrder("John", "Daisy");

        assertThat(result)
                .extracting(PatientDto::lastName)
                .containsExactlyInAnyOrder("Doe", "Doe");

    }
     /* ===============================
    Section: Schedule Entity Tests
    =============================== */
     @Test
     @DisplayName("Should add schedule")
     public void should_add_schedule() {
        //given
         LocalDate futureDate = LocalDate.of(2025, 5, 20);
         LocalTime startTime = LocalTime.of(9, 0);
         LocalTime endTime = LocalTime.of(18, 0);
         ScheduleRequestDto requestDto = new ScheduleRequestDto(
                 futureDate,
                 startTime,
                 endTime
         );
         //when
         ScheduleResponseDto response = clinicCrudFacade.addSchedule(requestDto);
         //then
         assertThat(response.id()).isEqualTo(0L);
         assertThat(response.date()).isEqualTo(futureDate);
         assertThat(response.date()).isEqualTo(LocalDate.of(2025, 5, 20));
         assertThat(response.startTime()).isEqualTo(startTime);
         assertThat(response.startTime()).isEqualTo(LocalTime.of(9, 0));
         assertThat(response.endTime()).isEqualTo(endTime);
         assertThat(response.endTime()).isEqualTo(LocalTime.of(18, 0));
         assertThat(response.dentistDto().id()).isEqualTo(1L);
         assertThat(response.dentistDto().firstName()).isEqualTo("Oskar");
         assertThat(response.dentistDto().lastName()).isEqualTo("Test");
     }

    /* ===============================
    Section: Appointment Entity Tests
    =============================== */
    // TC for -> findAppointmentDtoById
    @Test
    @DisplayName("Should retrieve appointment by id")
    public void should_retrieve_appointment_by_id() {
        //given

        //when

        //then

    }
}