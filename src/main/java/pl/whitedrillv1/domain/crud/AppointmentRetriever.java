package pl.whitedrillv1.domain.crud;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.whitedrillv1.domain.crud.dto.AppointmentDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentFirstAvailableHourDto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

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

    Set<AppointmentDto> findAll(Pageable pageable) {
        log.info("Retrieving all appointments: ");
        return appointmentRepository.findAll(pageable)
                .stream()
                .map(AppointmentMapper::mapFromAppointmentToAppointmentDto)
                .collect(Collectors.toSet());
    }

    public boolean hasUpcomingAppointments(Long patientId) {
        return appointmentRepository.existsByPatientIdAndStatusFromToday(
                patientId, AppointmentStatus.SCHEDULED);
    }

    public AppointmentFirstAvailableHourDto findFirstAvailableDate(Long dentistId) {
        List<Schedule> schedules = scheduleRepository.findAllByDentistIdOrderByDateAsc(dentistId);

        log.info("Found {} schedules for dentist with ID {}", schedules.size(), dentistId);

        // Iteruje przez harmonogramy, aby znaleźć pierwszy dostępny termin
        return schedules.stream()
                .map(schedule -> {
                    Integer firstAvailableHour = schedule.getFirstAvailableHour();
                    if (firstAvailableHour != null) {
                        log.info("First available hour found: {} on {}", firstAvailableHour, schedule.getDate());
                        return new AppointmentFirstAvailableHourDto(
                                schedule.getDate(),
                                LocalTime.of(firstAvailableHour, 0)
                        );
                    }
                    return null;
                })
                .filter(Objects::nonNull) // Usuwam puste wyniki
                .findFirst() // Zwraca pierwszy znaleziony dostępny termin
                .orElseThrow(() -> {
                    log.warn("No available dates found for dentist with ID {}", dentistId);
                    return new NoAvailableDateException("Obecnie brak dostępnych terminów dla dentysty o ID: " + dentistId);
                });
    }
}