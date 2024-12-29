package pl.whitedrillv1.domain.crud;


import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.whitedrillv1.domain.crud.dto.AppointmentBasicUpdateDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentDto;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log4j2
@Service
@AllArgsConstructor
@Transactional
class AppointmentUpdater {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentRetriever appointmentRetriever;
    private final ScheduleRepository scheduleRepository;

    public AppointmentDto updateBasicAppointmentFields(Long appointmentId, AppointmentBasicUpdateDto updateDto) {
        Appointment appointment = appointmentRetriever.findAppointmentById(appointmentId);
        Schedule schedule = appointment.getSchedule();

        // Sprawdź dostępność nowej daty (jeśli została podana)
        if (updateDto.appointmentDate() != null && !updateDto.appointmentDate().equals(appointment.getAppointmentDate())) {
            if (scheduleRepository.existsByDate(updateDto.appointmentDate())) {
                throw new DateAlreadyBookedException("Wybrana data jest już zajęta w harmonogramie.");
            }
            appointment.setAppointmentDate(updateDto.appointmentDate());
        }
        // Stare godziny
        Set<Integer> oldReservedHours = new HashSet<>(appointment.getReservedHours());

        // Nowe godziny
        Set<Integer> newReservedHours = calculateReservedHours(
                updateDto.appointmentTime() != null ? updateDto.appointmentTime() : appointment.getAppointmentTime(),
                updateDto.duration() != null ? updateDto.duration() : appointment.getDuration()
        );

        // Weryfikacja dostępności nowych godzin
        if (!areHoursAvailable(newReservedHours, schedule.getBookedHours(), oldReservedHours)) {
            throw new HoursAlreadyBookedException("Wybrane godziny są już zajęte w harmonogramie.");
        }

        // Aktualizacja godziny rozpoczęcia i długości wizyty
        if (updateDto.appointmentTime() != null) {
            appointment.setAppointmentTime(updateDto.appointmentTime());
        }
        if (updateDto.duration() != null) {
            appointment.setDuration(updateDto.duration());
        }

        // Aktualizacja ceny (jeśli podano)
        if (updateDto.price() != null) {
            appointment.setPrice(updateDto.price());
        }

        // Zaktualizuj godziny w Schedule
        schedule.getBookedHours().removeAll(oldReservedHours); // Usuń stare godziny
        schedule.getBookedHours().addAll(newReservedHours);   // Dodaj nowe godziny

        // Aktualizuj godziny w Appointment
        appointment.getReservedHours().clear();
        appointment.getReservedHours().addAll(newReservedHours);

        // Zapisz zmiany
        appointmentRepository.save(appointment);
        scheduleRepository.save(schedule);

        AppointmentDto dto = AppointmentMapper.mapFromAppointmentToAppointmentDto(appointment);

        return dto;
    }

    /**
    Czy ten kod nie trzeba pooprawić albo inaczej rozwiązać skoro już się powtarzaa ????!!
     */
    private Set<Integer> calculateReservedHours(LocalTime startTime, Integer duration) {
        int startHour = startTime.getHour();
        return IntStream.range(startHour, startHour + duration)
                .boxed()
                .collect(Collectors.toSet());
    }

    private boolean areHoursAvailable(Set<Integer> newReservedHours, Set<Integer> bookedHours, Set<Integer> oldReservedHours) {
        // Usuń stare godziny z bookedHours, aby sprawdzić tylko konflikt z nowymi godzinami
        Set<Integer> effectiveBookedHours = new HashSet<>(bookedHours);
        effectiveBookedHours.removeAll(oldReservedHours);
        return newReservedHours.stream().noneMatch(effectiveBookedHours::contains);
    }
}
