package pl.whitedrillv1.domain.crud;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;


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

    @Modifying
    @Query("""
            UPDATE Appointment a
                SET a.schedule.id = :scheduleId
                WHERE a.id = :appointmentId
            """)
    void addAppointmentToSchedule (Long scheduleId, Long appointmentId);

    @Query("""
        
        
        SELECT s
        FROM Schedule s
        WHERE s.dentist.id = :dentistId
        ORDER BY s.date ASC
       """)
    List<Schedule> findAllByDentistIdOrderByDateAsc(@Param("dentistId") Long dentistId);
}
