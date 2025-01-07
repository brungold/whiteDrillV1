package pl.whitedrillv1.domain.crud;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.whitedrillv1.domain.crud.dto.AppointmentBasicUpdateDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentDto;
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

    // Patient methods
    public void deletePatientById(Long id) {
        patientRetriever.findPatientById(id);
        patientDeleter.deletePatient(id);
    }

    // Dentist methods
    public DentistDto findDentistDtoById(Long id) {
        return dentistRetriever.findDentistDtoById(id);
    }
}