package pl.whitedrillv1.domain.crud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.whitedrillv1.domain.crud.dto.AppointmentDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentRequestDto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
@Transactional
class AppointmentAdder {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentRetriever appointmentRetriever;
    private final ScheduleRetriever scheduleRetriever;
    private final PatientRepository patientRepository;
    private final DentistRepository dentistRepository;

    AppointmentDto addAppointment(AppointmentRequestDto appointmentRequestDto) {
        LocalDate date = appointmentRequestDto.appointmentDate();
        LocalTime startTime = appointmentRequestDto.appointmentTime();
        Integer duration = appointmentRequestDto.duration();


        Schedule schedule = scheduleRetriever.findScheduleByDate(date);

        // 2. Wylicz godziny zajmowane przez wizytę
        List<Integer> reservedHours = calculateReservedHours(
                appointmentRequestDto.appointmentTime(),
                appointmentRequestDto.duration());

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
                .build();

        // 7. Zapisz wizytę
        appointmentRepository.save(appointment);

        // 8. Przypisuje wizytę do danego dnia
        schedule.addAppointment(appointment);

        //TODO
        //trzeba edytować schedule w bazie i dodać mu appointment

        return AppointmentMapper.mapFromAppointmentToAppointmentDto(appointment);
    }

    public void addAppointmentToSchedule(final Long ScheduleId,final Long AppointmentId) {
        Schedule schedule = scheduleRetriever.findScheduleById(ScheduleId);
        Appointment appointment = appointmentRetriever.findAppointmentById(AppointmentId);
        schedule.addAppointment(appointment);
    }

    private List<Integer> calculateReservedHours(LocalTime startTime, Integer duration) {
        int startHour = startTime.getHour(); // wrapping startTime into integer hour
        return IntStream.range(startHour, startHour + duration)
                .boxed() // wrapping into Integer obj
                .toList(); // return as list
    }

    private boolean areHoursAvailable(List<Integer> reservedHours, Set<Integer> bookedHours) {
        // Sprawdzam jakakolwiek godzina z `reservedHours` jest zajęta w `bookedHours`
        return reservedHours.stream().noneMatch(bookedHours::contains);
    }
}