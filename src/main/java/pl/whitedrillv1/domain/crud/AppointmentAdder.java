package pl.whitedrillv1.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.whitedrillv1.domain.crud.Dentist;
import pl.whitedrillv1.domain.crud.DentistNotFoundException;
import pl.whitedrillv1.domain.crud.DentistRepository;
import pl.whitedrillv1.domain.crud.HoursAlreadyBookedException;
import pl.whitedrillv1.domain.crud.Patient;
import pl.whitedrillv1.domain.crud.PatientNotFoundException;
import pl.whitedrillv1.domain.crud.PatientRepository;
import pl.whitedrillv1.domain.crud.Schedule;
import pl.whitedrillv1.domain.crud.ScheduleRetriever;
import pl.whitedrillv1.domain.crud.dto.AppointmentDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentRequestDto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
class AppointmentAdder {

    private final AppointmentRepository appointmentRepository;
    private final ScheduleRetriever scheduleRetriever;
    private final PatientRepository patientRepository;
    private final DentistRepository dentistRepository;

    AppointmentDto addAppointment(AppointmentRequestDto appointmentRequestDto) {
        LocalDate date = appointmentRequestDto.appointmentDate();
        LocalTime startTime = appointmentRequestDto.appointmentTime();
        Integer duration = appointmentRequestDto.duration();

        Schedule schedule = scheduleRetriever.findScheduleByDate(date);

        // 2. Wylicz godziny zajmowane przez wizytę
        Appointment tempAppointment = Appointment.builder()
                .appointmentTime(startTime)
                .duration(duration)
                .build();

        Set<Integer> reservedHours = tempAppointment.calculateReservedHours();

        // 3. Sprawdź, czy godziny są dostępne
        if (!areHoursAvailable(reservedHours, schedule.getBookedHours())) {
            throw new HoursAlreadyBookedException("Proszę sprawdź ponownie czy godziny : " + reservedHours + " nie sa już zarezerwowane.");
        }

        // 4. Pobierz pacjenta i dentystę
        Patient patient = patientRepository.findById(appointmentRequestDto.patientId())
                .orElseThrow(() -> new PatientNotFoundException("Pacjent nie znaleziony"));
        Dentist dentist = dentistRepository.findById(appointmentRequestDto.dentistId())
                .orElseThrow(() -> new DentistNotFoundException("Dentysta nie znaleziony"));

        // 5. Utwórz nową wizytę
        Appointment appointment = Appointment.builder()
                .appointmentDate(date)
                .appointmentTime(startTime)
                .duration(duration)
                .status(AppointmentStatus.SCHEDULED)
                .patient(patient)
                .dentist(dentist)
                .reservedHours(reservedHours)
                .schedule(schedule) // Ustawienie harmonogramu!
                .build();

//        // zamiast dodawać schedle w builder wyżej przypisuje przez set
//        appointment.setSchedule(schedule);

        // 6. Przypisuje wizytę do danego dnia
        schedule.addAppointment(appointment);
        schedule.addReservedHoursFromAppointment(appointment);

        // 7. Zapisz wizytę
        appointmentRepository.save(appointment);


        log.info("Appointment added: " + appointment);
        return AppointmentMapper.mapFromAppointmentToAppointmentDto(appointment);
    }
    /**
     * Sprawdza, czy godziny są dostępne w harmonogramie.
     *
     * @param reservedHours godziny zajmowane przez wizytę
     * @param bookedHours   zajęte godziny w harmonogramie
     * @return true, jeśli godziny są dostępne
     */
    private boolean areHoursAvailable(Set<Integer> reservedHours, Set<Integer> bookedHours) {
        return reservedHours.stream().noneMatch(bookedHours::contains);
    }
}