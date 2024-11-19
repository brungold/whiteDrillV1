package pl.whitedrillv1.domain.crud;

import org.springframework.data.repository.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

interface ScheduleRepository extends Repository<Schedule, Long> {
    Optional<Schedule> findById(Long id);

    Set<Schedule> findAll();

    Optional<Schedule> findByDate(LocalDate date);

    Schedule save(Schedule schedule);
}
