package pl.whitedrillv1.domain.crud;

import org.springframework.data.repository.Repository;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

interface ScheduleRepository extends Repository<Schedule, Long> {
    Optional<Schedule> findById(Long id);

    Optional<Schedule> findByDate(LocalDate date);

    Schedule save(Schedule schedule);

    boolean existsByDate(LocalDate date);

//    boolean existsById(Long id);


    /*
    Metoda która obiera kilka dni roboczych aby terminarz
    Set<Schedule> findAll(Pageable pageable);
    */
}
