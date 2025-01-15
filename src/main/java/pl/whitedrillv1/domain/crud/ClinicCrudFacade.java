package pl.whitedrillv1.domain.crud;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.whitedrillv1.domain.crud.dto.AddressDto;
import pl.whitedrillv1.domain.crud.dto.AddressRequestDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentBasicUpdateDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentFirstAvailableHourDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentFullUpdateDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentRequestDto;
import pl.whitedrillv1.domain.crud.dto.DentistDto;
import pl.whitedrillv1.domain.crud.dto.PatientDto;
import pl.whitedrillv1.domain.crud.dto.PatientRequestDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleAvailableHoursDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleRequestDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleResponseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ClinicCrudFacade {
    private final PatientAdder patientAdder;
    private final PatientRetriever patientRetriever;
    private final ScheduleAdder scheduleAdder;
    private final ScheduleRetriever scheduleRetriever;
    private final AppointmentAdder appointmentAdder;
    private final AppointmentRetriever appointmentRetriever;
    private final DentistRetriever dentistRetriever;
    private final AppointmentUpdater appointmentUpdater;
    private final PatientDeleter patientDeleter;
    private final AddressAdder addressAdder;
    private final AddressDeleter addressDeleter;


    //Address methods
    public AddressDto addAddressToPatient(Long patientId, AddressRequestDto addressRequestDto) {
        return addressAdder.addAddressToPatient(patientId, addressRequestDto);
    }

    public void deleteAddressFromPatient(Long patientId) {
        addressDeleter.deleteAddress(patientId);
    }

    // Patient methods
    public PatientDto addPatient(PatientRequestDto dto) {
        return patientAdder.addPatient(dto);
    }

    public Set<PatientDto> findAllPatients() {
        return patientRetriever.findAll();
    }

    public PatientDto findPatientDtoById(Long id) {
        return patientRetriever.findPatientDtoById(id);
    }

    public void deletePatientById(Long id) {
        patientRetriever.findPatientById(id);
        patientDeleter.deletePatient(id);
    }

    // Schedule methods
    public ScheduleResponseDto addSchedule(ScheduleRequestDto dto) {
        return scheduleAdder.addSchedule(dto);
    }

    public List<ScheduleDto> findAllSchedules(Pageable pageable) {
        return scheduleRetriever.findAllSchedules(pageable);
    }

    public ScheduleDto findScheduleDtoById(Long id) {
        return scheduleRetriever.findScheduleDtoById(id);
    }

    public ScheduleDto findScheduleDtoByDate(LocalDate date) {
        return scheduleRetriever.findScheduleDtoByDate(date);
    }

    public ScheduleAvailableHoursDto findAvailableHoursByDate(LocalDate date) {
        return scheduleRetriever.findAvailableHoursByDate(date);
    }

    // Appointment methods
    public AppointmentDto addAppointment(AppointmentRequestDto dto) {
        return appointmentAdder.addAppointment(dto);
    }

    public AppointmentDto findAppointmentDtoById(Long id) {
        return appointmentRetriever.findAppointmentDtoById(id);
    }

    public AppointmentDto basicAppointmentUpdate(Long id, AppointmentBasicUpdateDto dto) {
        return appointmentUpdater.updateBasicAppointmentFields(id, dto);
    }

    public AppointmentFirstAvailableHourDto findFirstAvailableHours(Long dentistId) {
        return appointmentRetriever.findFirstAvailableDate(dentistId);
    }

    public Set<AppointmentDto> findAllAppointmentsDto(Pageable pageable) {
        return appointmentRetriever.findAll(pageable);
    }

    public AppointmentDto updateAppointment(Long appointmentId, AppointmentFullUpdateDto dto) {
        return appointmentUpdater.updateAppointment(appointmentId,dto);
    }

    // Dentist methods
    public DentistDto findDentistDtoById(Long id) {
        return dentistRetriever.findDentistDtoById(id);
    }

}