package pl.whitedrillv1.domain.crud;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

class InMemoryScheduleRepository implements ScheduleRepository {

    Map<Long, Schedule> db = new HashMap<>();
    AtomicInteger index = new AtomicInteger(0);

    @Override
    public Optional<Schedule> findById(Long id) {
        Schedule schedule = db.get(id);
        return Optional.ofNullable(schedule);
    }

    @Override
    public Optional<Schedule> findByDate(LocalDate date) {
        return db.values().stream()
                .filter(schedule -> schedule.getDate()
                        .equals(date))
                .findFirst();
    }

    @Override
    public Schedule save(Schedule schedule) {
        long id = this.index.getAndIncrement();
        schedule.setId(id);
        db.put(id, schedule);
        return schedule;
    }

    @Override
    public boolean existsByDate(LocalDate date) {
        return db.values().stream()
                .anyMatch(schedule -> schedule.getDate().equals(date));
    }

    //TODO
    @Override
    public List<Schedule> findAll(final Pageable pageable) {
        return db.values().stream().toList();
    }

    @Override
    public void addAppointmentToSchedule(Long scheduleId, Long appointmentId) {
        Schedule schedule = db.get(scheduleId);
        Appointment appointment = schedule.getAppointments().stream()
                .filter(a -> a.getId().equals(appointmentId))
                .findFirst()
                .orElse(null);

        // Ustaw relację dwukierunkową
        schedule.addAppointment(appointment);
        appointment.setSchedule(schedule);

        // Zaktualizuj mapę
        db.put(scheduleId, schedule);
    }
}
