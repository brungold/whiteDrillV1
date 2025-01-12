package pl.whitedrillv1.domain.crud;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.data.domain.Pageable;
import pl.whitedrillv1.domain.crud.dto.AddressDto;
import pl.whitedrillv1.domain.crud.dto.AddressRequestDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentBasicUpdateDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentFirstAvailableHourDto;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Log4j2
public class ClinicCrudFacadeTest implements TestDataFactory, TestDataPatientFactory, TestDataAppointmentFactory{

    ClinicCrudFacade clinicCrudFacade = ClinicCrudFacadeConfiguration.createClinicCrudFacade(
            new InMemoryPatientRepository(),
            new InMemoryScheduleRepository(),
            new InMemoryDentistRepository(),
            new InMemoryAppointmentRepository(),
            new InMemoryAddressRepository()
    );

    /* ==========================
    Section: Patient Entity Tests
    ========================== */
    // TC for -> addPatient
    @Test
    @DisplayName("Should add patient john test with id: 0 when john was sent")
    public void should_add_patient_john_test_with_id_zero_when_john_was_sent() {
        //given
        PatientRequestDto patient = createDefaultPatientRequestDtoNo1();

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
        PatientRequestDto patient = createDefaultPatientRequestDtoNo1();
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
        PatientRequestDto patientNo1 = createDefaultPatientRequestDtoNo1();
        PatientRequestDto patientNo2 = createDefaultPatientRequestDtoNo2();

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

    // TC for deletePatientById
    @Test
    @DisplayName("Should delete patient with id 2 and verify remaining patients")
    public void should_delete_patient_with_id_2_and_verify_remaining_patients() {
        // given
        PatientRequestDto patientNo1 = createDefaultPatientRequestDtoNo1();
        PatientRequestDto patientNo2 = createDefaultPatientRequestDtoNo2();

        // Dodaj pacjentów
        PatientDto addedPatient1 = clinicCrudFacade.addPatient(patientNo1);
        PatientDto addedPatient2 = clinicCrudFacade.addPatient(patientNo2);

        // Sprawdź, że dodano dwóch pacjentów
        Set<PatientDto> allPatientsBeforeDelete = clinicCrudFacade.findAllPatients();
        assertThat(allPatientsBeforeDelete).hasSize(2);
        assertThat(allPatientsBeforeDelete).extracting(PatientDto::id)
                .containsExactlyInAnyOrder(addedPatient1.id(), addedPatient2.id());

        // when
        clinicCrudFacade.deletePatientById(addedPatient2.id());

        // then
        Set<PatientDto> allPatientsAfterDelete = clinicCrudFacade.findAllPatients();
        assertThat(allPatientsAfterDelete).hasSize(1);
        assertThat(allPatientsAfterDelete).extracting(PatientDto::id)
                .containsExactly(addedPatient1.id());
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

    // // TC for -> findScheduleDtoByDate
    @Test
    @DisplayName("Should retrieve schedule by date")
    public void should_retrieve_schedule_by_date() {
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
        ScheduleDto scheduleDtoByDate = clinicCrudFacade.findScheduleDtoByDate(LocalDate.of(2025, 5, 22));

        //then
        assertThat(scheduleDtoByDate.date()).isEqualTo(futureDate3);
    }

    /* ===============================
    Section: Appointment Entity Tests
    =============================== */
    // TC for -> addAppointment
    @Test
    @DisplayName("Should add new appointment")
    public void should_add_new_appointment() {
        //given
        PatientRequestDto patient = createDefaultPatientRequestDtoNo1();
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

    // TC for -> basicAppointmentUpdate
    @Test
    @DisplayName("Should update basic data for an Appointment at the same day")
    public void should_update_basic_appointment_data_at_the_same_day() {
        // given
        PatientRequestDto patient = createDefaultPatientRequestDtoNo1();
        PatientDto patientDto = clinicCrudFacade.addPatient(patient);

        ScheduleRequestDto scheduleRequestDto = createDefaultScheduleRequestDtoNo1();
        ScheduleResponseDto firstSchedule = clinicCrudFacade.addSchedule(scheduleRequestDto);

        AppointmentRequestDto firstAppointmentRequestDto = createDefaultAppointmentRequestDto(
                patientDto.id(),
                1L,
                LocalDate.of(2025, 5, 20),
                LocalTime.of(10, 0),
                1
        );
        AppointmentDto firstAppointmentDto = clinicCrudFacade.addAppointment(firstAppointmentRequestDto);
        Long firstAppointmentDtoId = firstAppointmentDto.id();

        AppointmentRequestDto secondAppointmentRequestDto = createDefaultAppointmentRequestDto(
                patientDto.id(),
                1L,
                LocalDate.of(2025, 5, 20),
                LocalTime.of(9, 0),
                1
        );
        AppointmentDto secondAppointmentDto = clinicCrudFacade.addAppointment(secondAppointmentRequestDto);
        Long secondAppointmentDtoId = secondAppointmentDto.id();

        // when
        AppointmentBasicUpdateDto updateSecondAppointmentDto = createAppointmentBasicUpdateDto(
                LocalTime.of(12, 0),
                2
        );
        clinicCrudFacade.basicAppointmentUpdate(secondAppointmentDtoId, updateSecondAppointmentDto);

        //then
        AppointmentDto unchangedAppointmentDto = clinicCrudFacade.findAppointmentDtoById(firstAppointmentDtoId);
        AppointmentDto updatedAppointmentDto = clinicCrudFacade.findAppointmentDtoById(secondAppointmentDtoId);
            // Assert for the unchanged appointment
        assertThat(unchangedAppointmentDto).isNotNull();
        assertThat(unchangedAppointmentDto.appointmentTime()).isEqualTo(LocalTime.of(10, 0));
        assertThat(unchangedAppointmentDto.duration()).isEqualTo(1);
        assertThat(unchangedAppointmentDto.patientId()).isEqualTo(patientDto.id());
        assertThat(unchangedAppointmentDto.dentistId()).isEqualTo(1L);
        assertThat(unchangedAppointmentDto.appointmentDate()).isEqualTo(LocalDate.of(2025, 5, 20));
            // Assert for the updated appointment
        assertThat(updatedAppointmentDto).isNotNull();
        assertThat(updatedAppointmentDto.appointmentTime()).isEqualTo(LocalTime.of(12, 0));
        assertThat(updatedAppointmentDto.duration()).isEqualTo(2);
        assertThat(updatedAppointmentDto.patientId()).isEqualTo(patientDto.id());
        assertThat(updatedAppointmentDto.dentistId()).isEqualTo(1L);
        assertThat(updatedAppointmentDto.appointmentDate()).isEqualTo(LocalDate.of(2025, 5, 20));
    }

    //TC for -> findFirstAvailableHours
    @Test
    @DisplayName("Should find first available hours for appointment")
    public void should_find_first_available_hours_for_appointment() {
        //given
        PatientRequestDto patientNo1 = createDefaultPatientRequestDtoNo1();
        PatientRequestDto patientNo2 = createDefaultPatientRequestDtoNo2();
        PatientRequestDto patientNo3 = createDefaultPatientRequestDtoNo3();
        PatientRequestDto patientNo4 = createDefaultPatientRequestDtoNo4();

        ScheduleRequestDto scheduleRequestDtoNo1 = createDefaultScheduleRequestDtoNo1();
        ScheduleRequestDto scheduleRequestDtoNo2 = createDefaultScheduleRequestDtoNo2();
        ScheduleRequestDto scheduleRequestDtoNo3 = createDefaultScheduleRequestDtoNo3();

        PatientDto patientDto1 = clinicCrudFacade.addPatient(patientNo1);
        PatientDto patientDto2 = clinicCrudFacade.addPatient(patientNo2);
        PatientDto patientDto3 = clinicCrudFacade.addPatient(patientNo3);
        PatientDto patientDto4 = clinicCrudFacade.addPatient(patientNo4);

        clinicCrudFacade.addSchedule(scheduleRequestDtoNo1);
        clinicCrudFacade.addSchedule(scheduleRequestDtoNo2);
        clinicCrudFacade.addSchedule(scheduleRequestDtoNo3);
        ScheduleDto scheduleDto= clinicCrudFacade.findScheduleDtoByDate(scheduleRequestDtoNo1.date());


            //Schedule (scheduleRequestDtoNo1) =  12 - 18
        AppointmentRequestDto defaultAppointmentRequestDto1 = createDefaultAppointmentRequestDto(patientDto1.id(), 1L, scheduleRequestDtoNo1.date(), LocalTime.of(12, 0), 3);
        clinicCrudFacade.addAppointment(defaultAppointmentRequestDto1);
        AppointmentRequestDto defaultAppointmentRequestDto2 = createDefaultAppointmentRequestDto(patientDto2.id(), 1L, scheduleRequestDtoNo1.date(), LocalTime.of(15, 0), 1);
        clinicCrudFacade.addAppointment(defaultAppointmentRequestDto2);
        AppointmentRequestDto defaultAppointmentRequestDto3 = createDefaultAppointmentRequestDto(patientDto3.id(), 1L, scheduleRequestDtoNo1.date(), LocalTime.of(16, 0), 2);
        clinicCrudFacade.addAppointment(defaultAppointmentRequestDto3);

        //Schedule (scheduleRequestDtoNo2) =  11 - 19
        AppointmentRequestDto defaultAppointmentRequestDto4 = createDefaultAppointmentRequestDto(patientDto4.id(), 1L, scheduleRequestDtoNo2.date(), LocalTime.of(11, 0), 1);
        clinicCrudFacade.addAppointment(defaultAppointmentRequestDto4);
        AppointmentRequestDto defaultAppointmentRequestDto5 = createDefaultAppointmentRequestDto(patientDto2.id(), 1L, scheduleRequestDtoNo2.date(), LocalTime.of(12, 0), 1);
        clinicCrudFacade.addAppointment(defaultAppointmentRequestDto5);
        AppointmentRequestDto defaultAppointmentRequestDto6 = createDefaultAppointmentRequestDto(patientDto1.id(), 1L, scheduleRequestDtoNo2.date(), LocalTime.of(13, 0), 3);
        clinicCrudFacade.addAppointment(defaultAppointmentRequestDto6);
        AppointmentRequestDto defaultAppointmentRequestDto7 = createDefaultAppointmentRequestDto(patientDto4.id(), 1L, scheduleRequestDtoNo2.date(), LocalTime.of(16, 0), 2);
        clinicCrudFacade.addAppointment(defaultAppointmentRequestDto7);
        AppointmentRequestDto defaultAppointmentRequestDto8 = createDefaultAppointmentRequestDto(patientDto3.id(), 1L, scheduleRequestDtoNo2.date(), LocalTime.of(18, 0), 1);
        clinicCrudFacade.addAppointment(defaultAppointmentRequestDto8);
            //Schedule (scheduleRequestDtoNo3) =  10 - 18
        AppointmentRequestDto defaultAppointmentRequestDto9 = createDefaultAppointmentRequestDto(patientDto4.id(), 1L, scheduleRequestDtoNo3.date(), LocalTime.of(10, 0), 1);
        clinicCrudFacade.addAppointment(defaultAppointmentRequestDto9);
        AppointmentRequestDto defaultAppointmentRequestDto10 = createDefaultAppointmentRequestDto(patientDto2.id(), 1L, scheduleRequestDtoNo3.date(), LocalTime.of(11, 0), 2);
        clinicCrudFacade.addAppointment(defaultAppointmentRequestDto10);


        List<ScheduleDto> allSchedules = clinicCrudFacade.findAllSchedules(Pageable.unpaged());
        log.info("All schedules: {}", allSchedules);
        assertThat(allSchedules).hasSize(3);
        Set<AppointmentDto> appointmentDtos = clinicCrudFacade.findAllAppointmentsDto(Pageable.unpaged());
        log.info("All appointments: {}", appointmentDtos);
        assertThat(appointmentDtos).hasSize(10);
        // when
        AppointmentFirstAvailableHourDto firstAvailableHours = clinicCrudFacade.findFirstAvailableHours(1L);

        //then
        assertThat(firstAvailableHours).isEqualTo(new AppointmentFirstAvailableHourDto(LocalDate.of(2025, 5,22),LocalTime.of(13, 0)));
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
    /* ==========================
    Section: Address Entity Tests
    ========================== */
    // TC for -> addressAdder
    @Test
    @DisplayName("Should add address to created patient")
    public void should_add_address_to_created_patient() {
        //given
        PatientRequestDto patientWithoutAddress = createDefaultPatientRequestDtoWithoutAddress();
        PatientDto patientDto = clinicCrudFacade.addPatient(patientWithoutAddress);
        Long patientId = patientDto.id();

        AddressRequestDto addressRequestDto = AddressRequestDto.builder()
                .postalCode("12-345")
                .city("Warszawa")
                .street("Marszałkowska")
                .houseNumber("10A")
                .apartmentNumber("15")
                .build();
        //when

        AddressDto addressDto = clinicCrudFacade.addAddressToPatient(patientId, addressRequestDto);
        //then
        assertNotNull(addressDto);
        assertEquals("12-345", addressDto.postalCode());
        assertEquals("Warszawa", addressDto.city());
    }
    // TC for -> addressAdder
    @Test
    @DisplayName("Should delete address")
    public void should_delete_address() {
        // given
        PatientRequestDto patientNo1 = createDefaultPatientRequestDtoNo1();
        PatientRequestDto patientNo2 = createDefaultPatientRequestDtoNo2();

        PatientDto patientDto1 = clinicCrudFacade.addPatient(patientNo1);
        clinicCrudFacade.addPatient(patientNo2);

        Long patientDto1Id = patientDto1.id();

            // Weryfikuje, że adres początkowo istnieje
        AddressDto initialAddress = patientDto1.address();
        assertThat(initialAddress).isNotNull();
        assertThat(initialAddress).isEqualTo(AddressDto.builder()
                .postalCode("12-345")
                .city("New York")
                .street("Broadway")
                .houseNumber("1")
                .apartmentNumber("101")
                .build());
        //when
        clinicCrudFacade.deleteAddressFromPatient(patientDto1Id);
        // then
            // Pobieram ponownie pacjenta i sprawdzam, czy adres został usunięty
        PatientDto updatedPatient = clinicCrudFacade.findPatientDtoById(patientDto1Id);
        assertThat(updatedPatient.address()).isNull();
    }
}