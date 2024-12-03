package pl.whitedrillv1.domain.crud;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.data.domain.Pageable;
import pl.whitedrillv1.domain.crud.dto.AddressDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentRequestDto;
import pl.whitedrillv1.domain.crud.dto.PatientDto;
import pl.whitedrillv1.domain.crud.dto.PatientGenderDto;
import pl.whitedrillv1.domain.crud.dto.PatientRequestDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleAvailableHoursDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleRequestDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleResponseDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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
     // TC for -> addSchedule
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
    // TC for -> InvalidTimeRangeException
    @Test
    @DisplayName("Should throw InvalidTimeRangeException when end time is before start time")
    public void should_throw_InvalidTimeRangeException_when_end_time_is_before_start_time() {
        //given
        LocalDate futureDate = LocalDate.of(2025, 5, 20);
        LocalTime startTime = LocalTime.of(18, 0);
        LocalTime endTime = LocalTime.of(9, 0);
        ScheduleRequestDto requestDto = new ScheduleRequestDto(
                futureDate,
                startTime,
                endTime
        );
        // when
        Throwable throwable = catchThrowable(() -> clinicCrudFacade.addSchedule(requestDto));

        // then
        assertThat(throwable)
                .isInstanceOf(ScheduleInvalidTimeRangeException.class)
                .hasMessage("Godzina rozpoczęcia dnia pracy, nie może być późniejsza niż godzina końca dnia pracy.");

    }

    @Test
    @DisplayName("Should throw ScheduleAlreadyExistsException when schedule for the same date is added twice")
    public void should_throw_ScheduleAlreadyExistsException_when_schedule_for_the_same_date_is_added_twice() {
        // given
        LocalDate scheduleDate = LocalDate.of(2025, 5, 20);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);

        ScheduleRequestDto firstRequest = new ScheduleRequestDto(
                scheduleDate,
                startTime,
                endTime
        );

        ScheduleRequestDto secondRequest = new ScheduleRequestDto(
                scheduleDate,
                startTime,
                endTime
        );

        ScheduleResponseDto firstResponse = clinicCrudFacade.addSchedule(firstRequest);
        assertThat(firstResponse.date()).isEqualTo(scheduleDate);
        assertThat(firstResponse.startTime()).isEqualTo(startTime);
        assertThat(firstResponse.endTime()).isEqualTo(endTime);

        // when
        Throwable throwable = catchThrowable(() -> clinicCrudFacade.addSchedule(secondRequest));

        // then
        assertThat(throwable)
                .isInstanceOf(ScheduleAlreadyExistsException.class)
                .hasMessage("Dla tej daty 2025-05-20 jest już utworzony grafik pracy.");
    }

    //TC for findAllSchedules
    @Test
    @DisplayName("Should retrieve all schedules")
    public void should_retrieve_all_schedules() {
        //given
        LocalDate futureDate = LocalDate.of(2025, 5, 20);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        ScheduleRequestDto requestDto = new ScheduleRequestDto(
                futureDate,
                startTime,
                endTime
        );

        LocalDate futureDate2 = LocalDate.of(2025, 5, 21);
        LocalTime startTime2 = LocalTime.of(10, 0);
        LocalTime endTime2 = LocalTime.of(20, 0);
        ScheduleRequestDto requestDto2 = new ScheduleRequestDto(
                futureDate2,
                startTime2,
                endTime2
        );

        LocalDate futureDate3 = LocalDate.of(2025, 5, 22);
        LocalTime startTime3 = LocalTime.of(9, 0);
        LocalTime endTime3 = LocalTime.of(20, 0);
        ScheduleRequestDto requestDto3 = new ScheduleRequestDto(
                futureDate3,
                startTime3,
                endTime3
        );

        ScheduleResponseDto schedule = clinicCrudFacade.addSchedule(requestDto);
        ScheduleResponseDto schedule2 = clinicCrudFacade.addSchedule(requestDto2);
        ScheduleResponseDto schedule3 = clinicCrudFacade.addSchedule(requestDto3);

        //when
        List<ScheduleDto> allSchedules = clinicCrudFacade.findAllSchedules(Pageable.unpaged());
        ScheduleAvailableHoursDto availableHoursForFutureDate = clinicCrudFacade.findAvailableHoursByDate(futureDate);

        //then
        assertThat(allSchedules).isNotNull();
        assertThat(allSchedules).hasSize(3);

        // Verify available hours for a specific schedule
        assertThat(availableHoursForFutureDate).isNotNull();
        assertThat(availableHoursForFutureDate.date()).isEqualTo(futureDate);
        assertThat(availableHoursForFutureDate.availableHours()).isNotEmpty();
        assertThat(availableHoursForFutureDate.availableHours()).containsExactly(
                9, 10, 11, 12, 13, 14, 15, 16, 17  // Expected hours
        );
    }




    /* ===============================
    Section: Appointment Entity Tests
    =============================== */
    // TC for -> addAppointment
    @Test
    @DisplayName("Should add new appointment")
    public void should_add_new_appointment() {
        //given
        PatientRequestDto patient = PatientRequestDto.builder()
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
        PatientDto patientDto = clinicCrudFacade.addPatient(patient);

        assertThat(patientDto).isNotNull();
        assertThat(patientDto.id()).isEqualTo(0L);
        assertThat(patientDto.firstName()).isEqualTo("John");
        assertThat(patientDto.lastName()).isEqualTo("Doe");
        assertThat(patientDto.gender()).isEqualTo("MALE");
        assertThat(patientDto.pesel()).isEqualTo("80051512345");
        assertThat(patientDto.email()).isEqualTo("john.doe@example.com");
        assertThat(patientDto.address().city()).isEqualTo("New York");
        LocalDate futureDate = LocalDate.of(2025, 5, 20);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(18, 0);

        ScheduleRequestDto scheduleRequestDto = new ScheduleRequestDto(
                futureDate,
                startTime,
                endTime
        );

        ScheduleResponseDto response = clinicCrudFacade.addSchedule(scheduleRequestDto);

        assertThat(response.id()).isEqualTo(0L);
        assertThat(response.date()).isEqualTo(futureDate);
        assertThat(response.startTime()).isEqualTo(startTime);
        assertThat(response.endTime()).isEqualTo(endTime);
        assertThat(response.dentistDto().id()).isEqualTo(1L);

        AppointmentRequestDto appointmentRequestDto = AppointmentRequestDto.builder()
                .appointmentDate(LocalDate.of(2025, 5, 20))
                .appointmentTime(LocalTime.of(9, 0))
                .duration(1)
                .patientId(patientDto.id())
                .dentistId(1L)
                .build();

        //when
        AppointmentDto appointmentDto = clinicCrudFacade.addAppointment(appointmentRequestDto);
        Long AppointmentDtoId = appointmentDto.id();
        //     TC for -> findAppointmentDtoById
        AppointmentDto appointmentDtoById = clinicCrudFacade.findAppointmentDtoById(AppointmentDtoId);
        ScheduleDto scheduleDtoById = clinicCrudFacade.findScheduleDtoById(AppointmentDtoId);

        //then
        assertThat(appointmentDto).isNotNull();
        assertThat(appointmentDtoById).isNotNull();
        assertThat(appointmentDto.id()).isEqualTo(AppointmentDtoId);
        assertThat(appointmentDtoById.appointmentDate()).isEqualTo(LocalDate.of(2025, 5, 20));
        assertThat(appointmentDtoById.appointmentTime()).isEqualTo(LocalTime.of(9, 0));
        assertThat(appointmentDtoById.dentistId()).isEqualTo(1L);
        assertThat(appointmentDtoById.patientName()).isEqualTo("John Doe");
        assertThat(appointmentDtoById.duration()).isEqualTo(1);

        assertThat(scheduleDtoById.appointments().size()).isEqualTo(1);
        assertThat(scheduleDtoById.appointments())
                .anyMatch(appointment ->
                        appointment.appointmentDate().equals(LocalDate.of(2025, 5, 20)) &&
                                appointment.appointmentTime().equals(LocalTime.of(9, 0)) &&
                                appointment.dentistId().equals(1L) &&
                                appointment.patientId().equals(patientDto.id()) &&
                                appointment.patientName().equals("John Doe")
                );

    }
//     TC for -> findAppointmentDtoById
//    @Test
//    @DisplayName("Should retrieve appointment by id")
//    public void should_retrieve_appointment_by_id() {
//        //given
//
//        //when
//
//        //then
//
//    }


}