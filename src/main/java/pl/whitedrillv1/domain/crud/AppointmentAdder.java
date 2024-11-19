package pl.whitedrillv1.domain.crud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.whitedrillv1.domain.crud.dto.AppointmentDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentRequestDto;

@Service
@AllArgsConstructor
class AppointmentAdder {

    private final AppointmentRetriever appointmentRetriever;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DentistRepository dentistRepository;

/*
    AppointmentDto addAppointment(AppointmentRequestDto appointmentRequestDto) {

        Patient patient = patientRepository.findById(appointmentRequestDto.patientId())
                .orElseThrow(() -> new PatientNotFoundException("Patient with ID: " + appointmentRequestDto.patientId() + " not found."));

        Dentist dentist = dentistRepository.findById(appointmentRequestDto.dentistId())
                .orElseThrow(() -> new DentistNotFoundException("Dentist with ID: " + appointmentRequestDto.dentistId() + " not found."));

        Appointment appointment = Appointment.builder()
                .appointmentDate(appointmentRequestDto.appointmentDate())
                .appointmentTime(appointmentRequestDto.appointmentTime())
                .duration(appointmentRequestDto.duration() > 0 ? appointmentRequestDto.duration() : 60) // Domyślnie 60 minut
                .price(appointmentRequestDto.price() != null ? appointmentRequestDto.price() : BigDecimal.ZERO) // Domyślnie 0, jeśli brak
                .status(AppointmentStatus.valueOf(appointmentRequestDto.status().toUpperCase()))
                .appointmentNotes(appointmentRequestDto.appointmentNotes())
                .patient(patient)
                .dentist(dentist)
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);
    }

*/

}
