package pl.whitedrillv1.domain.crud;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

class InMemoryAppointmentRepository implements AppointmentRepository {

    Map<Long, Appointment> db = new HashMap<>();
    AtomicInteger index = new AtomicInteger(0);

    @Override
    public Optional<Appointment> findById(final Long id) {
        Appointment appointment = db.get(id);
        return Optional.ofNullable(appointment);
    }

    @Override
    public Set<Appointment> findAll() {
        return Set.of();
    }

    @Override
    public Appointment save(Appointment appointment) {
        long id = this.index.getAndIncrement();
        appointment.setId(id);
        db.put(id, appointment);
        return appointment;
    }

    @Override
    public void deleteById(Long id) {
        db.remove(id);
    }
}
