package pl.whitedrillv1.domain.crud;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

interface ScheduleRepository extends Repository<Schedule, Long> {
    Optional<Schedule> findById(Long id);

    Optional<Schedule> findByDate(LocalDate date);

    Schedule save(Schedule schedule);

    boolean existsByDate(LocalDate date);

//    @Query("""
//            SELECT s FROM Schedule s
//            JOIN FETCH s.appointments
//            """)
    List<Schedule> findAll(Pageable pageable);
}
