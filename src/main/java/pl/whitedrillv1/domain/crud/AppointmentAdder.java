package pl.whitedrillv1.domain.crud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.whitedrillv1.domain.crud.dto.AppointmentDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentRequestDto;
import pl.whitedrillv1.domain.crud.dto.ScheduleDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
@Transactional
class AppointmentAdder {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DentistRepository dentistRepository;
    private final ScheduleRetriever scheduleRetriever;
    /*
    addAppointment
    -> AppointmentRequestDto
        -> verification if there is a Schedule on that day
        -> verification if these hours are free
    -> create new Appoitment

    -> add to bookedHours hours form Request to database
    -> ScheduleUpdate
    -> return AppointmentResponseDto(id, data, hours, patient, dentist)
     */

    AppointmentDto addAppointment(AppointmentRequestDto appointmentRequestDto) {
        LocalDate date = appointmentRequestDto.appointmentDate();
        LocalTime startTime = appointmentRequestDto.appointmentTime();
        Integer duration = appointmentRequestDto.duration();

        ScheduleDto scheduleDto = scheduleRetriever.findScheduleByDate(date);


        // 2. Wylicz godziny zajmowane przez wizytę
        List<Integer> reservedHours = calculateReservedHours(
                appointmentRequestDto.appointmentTime(),
                appointmentRequestDto.duration());

        // 3. Sprawdź, czy godziny są dostępne
        if (!areHoursAvailable(reservedHours, scheduleDto.bookedHours())) {
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

        //TODO

        // 8. dodaj do appointments i hours w schedule czy wyciągać scheduleDto czy schedule ??
        //        Schedule schedule = scheduleRetriever.findScheduleByDate(date);

        return AppointmentMapper.mapFromAppointmentToAppointmentDto(appointment);
    }
    private List<Integer> calculateReservedHours(LocalTime startTime, Integer duration) {
        int startHour = startTime.getHour(); // wrapping startTime into integer hour
        return IntStream.range(startHour, startHour + duration)
                .boxed() // wrapping into Integer obj
                .toList(); // return as list
    }

    private boolean areHoursAvailable(List<Integer> reservedHours, Set<Integer> bookedHours) {
        // Sprawdź, czy jakakolwiek godzina z `reservedHours` jest zajęta w `bookedHours`
        return reservedHours.stream().noneMatch(bookedHours::contains);
    }


}
