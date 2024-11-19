package pl.whitedrillv1.domain.crud;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
    public Set<Schedule> findAll() {
        return new HashSet<>(db.values());
    }

    @Override
    public Optional<Schedule> findByDate(LocalDate date) {
        Schedule schedule = db.get(date);
        return Optional.ofNullable(schedule);
    }

    @Override
    public Schedule save(Schedule schedule) {
        long index = this.index.getAndIncrement();
        db.put(index, schedule);
        schedule.setId(index);
        return schedule;
    }
}
