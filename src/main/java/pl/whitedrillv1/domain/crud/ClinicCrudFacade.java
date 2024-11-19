package pl.whitedrillv1.domain.crud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.whitedrillv1.domain.crud.dto.PatientDto;
import pl.whitedrillv1.domain.crud.dto.PatientRequestDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleRequestDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleResponseDto;

import java.util.Set;

@Service
@AllArgsConstructor
public class ClinicCrudFacade {

    private final PatientAdder patientAdder;
    private final PatientRetriever patientRetriever;
    private final ScheduleAdder scheduleAdder;

    // Patient methods
    public PatientDto addPatient(PatientRequestDto dto) {
        return  patientAdder.addPatient(dto);
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
    // Appointment methods
//    public AppointmentDto findAppointmentDtoById(Long id) {
//        return appointmentRetriever.findById(id);
//    }
}
