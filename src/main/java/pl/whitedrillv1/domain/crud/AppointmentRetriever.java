package pl.whitedrillv1.domain.crud;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.whitedrillv1.domain.crud.dto.AppointmentDto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.TreeSet;

@Service
@Log4j2
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AppointmentRetriever {

    private final AppointmentRepository appointmentRepository;
    private final ScheduleRepository scheduleRepository;

    Appointment findAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Wizyta o podanym id: " + appointmentId + " nie istanieje."));
    }
    AppointmentDto findAppointmentDtoById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .map(AppointmentMapper::mapFromAppointmentToAppointmentDto)
                .orElseThrow(() -> new AppointmentNotFoundException("Wizyta o podanym id: " + appointmentId + " nie istanieje."));
    }

    public boolean hasUpcomingAppointments(Long patientId) {
        return appointmentRepository.existsByPatientIdAndStatusFromToday(
                patientId, AppointmentStatus.SCHEDULED);
    }

    public LocalDateTime findFirstAvailableDate(Long dentistId) {
        List<Schedule> schedules = scheduleRepository.findAllByDentistIdOrderByDateAsc(dentistId);
        for (Schedule schedule : schedules) {
            TreeSet<Integer> availableHours = schedule.getAvailableHours();
            if (!availableHours.isEmpty()) {
                return LocalDateTime.of(schedule.getDate(), LocalTime.of(availableHours.first(), 0));
            }
        }
        throw new NoAvailableDateException("Brak dostępnych terminów dla dentysty o ID: " + dentistId);
    }
}