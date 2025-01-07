package pl.whitedrillv1.domain.crud;


import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.whitedrillv1.domain.crud.dto.AppointmentBasicUpdateDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentFullUpdateDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
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
    private final PatientRepository patientRepository;

    public AppointmentDto updateBasicAppointmentFields(Long appointmentId, AppointmentBasicUpdateDto updateDto) {
        Appointment appointment = appointmentRetriever.findAppointmentById(appointmentId);
        Schedule schedule = appointment.getSchedule();

        LocalDate newDate = updateDto.appointmentDate();
        LocalTime newTime = updateDto.appointmentTime();
        Integer newDuration = updateDto.duration();

        // Ustaw datę wizyty
        LocalDate dateToUse = newDate != null ? newDate : appointment.getAppointmentDate();

        // Walidacja nowej daty i godzin
        validateNewAppointmentHours(appointment, dateToUse, newTime, newDuration);

        // Aktualizacja danych wizyty
        appointment.setAppointmentDate(dateToUse);
        appointment.setAppointmentTime(newTime);
        appointment.setDuration(newDuration);

        // Aktualizacja zajętych godzin
        Set<Integer> newReservedHours = calculateReservedHours(newTime, newDuration);
        schedule.getBookedHours().removeAll(appointment.getReservedHours());
        schedule.getBookedHours().addAll(newReservedHours);
        appointment.setReservedHours(newReservedHours);

        appointmentRepository.save(appointment);
        scheduleRepository.save(schedule);

        return AppointmentMapper.mapFromAppointmentToAppointmentDto(appointment);
    }

    public AppointmentDto updateAppointment(Long appointmentId, AppointmentFullUpdateDto updateDto) {
        // Pobierz wizytę
        Appointment appointment = appointmentRetriever.findAppointmentById(appointmentId);

        // Aktualizacja daty i godzin wizyty
        LocalDate newDate = updateDto.appointmentDate() != null ? updateDto.appointmentDate() : appointment.getAppointmentDate();
        LocalTime newTime = updateDto.appointmentTime() != null ? updateDto.appointmentTime() : appointment.getAppointmentTime();
        Integer newDuration = updateDto.duration() != null ? updateDto.duration() : appointment.getDuration();

        // Walidacja daty i godzin
        validateNewAppointmentHours(appointment, newDate, newTime, newDuration);

        // Aktualizacja danych wizyty
        if (updateDto.appointmentDate() != null) {
            appointment.setAppointmentDate(newDate);
        }
        if (updateDto.appointmentTime() != null) {
            appointment.setAppointmentTime(newTime);
        }
        if (updateDto.duration() != null) {
            appointment.setDuration(newDuration);
        }

        // Aktualizacja pacjenta
        if (updateDto.patientId() != null && !updateDto.patientId().equals(appointment.getPatient().getId())) {
            Patient patient = patientRepository.findById(updateDto.patientId())
                    .orElseThrow(() -> new PatientNotFoundException("Pacjent o ID " + updateDto.patientId() + " nie istnieje."));
            appointment.setPatient(patient);
        }
        /**
            Aktualizacja dentysty
            Wymaganie biznesowe jeden dentysta, w tej chwili fragment niepotrzebny
        */
//        if (updateDto.dentistId() != null && !updateDto.dentistId().equals(appointment.getDentist().getId())) {
//            Dentist dentist = dentistRepository.findById(updateDto.dentistId())
//                    .orElseThrow(() -> new DentistNotFoundException("Dentysta o ID " + updateDto.dentistId() + " nie istnieje."));
//            appointment.setDentist(dentist);
//        }

        // Aktualizacja ceny
        if (updateDto.price() != null) {
            appointment.setPrice(updateDto.price());
        }

        // Aktualizacja notatek
        if (updateDto.appointmentNotes() != null) {
            appointment.setAppointmentNotes(updateDto.appointmentNotes());
        }

        // Zaktualizowanie zajętych godzin w harmonogramie
        Schedule currentSchedule = appointment.getSchedule();
        currentSchedule.getBookedHours().removeAll(appointment.getReservedHours());
        Set<Integer> newReservedHours = calculateReservedHours(newTime, newDuration);
        currentSchedule.getBookedHours().addAll(newReservedHours);
        appointment.setReservedHours(newReservedHours);

        // Zapis zmian w bazie
        appointmentRepository.save(appointment);
        scheduleRepository.save(currentSchedule);

        return AppointmentMapper.mapFromAppointmentToAppointmentDto(appointment);
    }

    private void validateNewAppointmentHours(Appointment appointment, LocalDate newDate, LocalTime newStartTime, Integer newDuration) {
        LocalDate currentDate = appointment.getAppointmentDate();
        Schedule currentSchedule = appointment.getSchedule();

        // 1. Jeśli data pozostaje taka sama, sprawdzamy tylko godziny
        if (newDate.equals(currentDate)) {
            Set<Integer> oldReservedHours = appointment.getReservedHours();
            Set<Integer> newReservedHours = calculateReservedHours(newStartTime, newDuration);

            if (!areHoursAvailable(newReservedHours, currentSchedule.getBookedHours(), oldReservedHours)) {
                throw new HoursAlreadyBookedException("Wybrane godziny są już zajęte w harmonogramie.");
            }
            return; // Nie musimy dalej sprawdzać
        }

        // 2. Jeśli data jest inna, sprawdzamy, czy istnieje harmonogram na tę datę
        Schedule newSchedule = scheduleRepository.findByDate(newDate)
                .orElseThrow(() -> new ScheduleNotFoundException("Nie znaleziono harmonogramu dla daty: " + newDate));

        // 3. Sprawdzamy, czy nowe godziny są dostępne w harmonogramie dla nowej daty
        Set<Integer> newReservedHours = calculateReservedHours(newStartTime, newDuration);
        if (!areHoursAvailable(newReservedHours, newSchedule.getBookedHours(), Collections.emptySet())) {
            throw new HoursAlreadyBookedException("Wybrane godziny są już zajęte w harmonogramie na datę: " + newDate);
        }
    }

    private Set<Integer> calculateReservedHours(LocalTime startTime, Integer duration) {
        int startHour = startTime.getHour();
        return IntStream.range(startHour, startHour + duration)
                .boxed()
                .collect(Collectors.toSet());
    }

    /**
     Usuń stare godziny z bookedHours, aby sprawdzić tylko konflikt z nowymi godzinami
     */
    private boolean areHoursAvailable(Set<Integer> newReservedHours, Set<Integer> bookedHours, Set<Integer> oldReservedHours) {
        Set<Integer> effectiveBookedHours = new HashSet<>(bookedHours);
        effectiveBookedHours.removeAll(oldReservedHours);
        return newReservedHours.stream().noneMatch(effectiveBookedHours::contains);
    }
}
