package pl.whitedrillv1.domain.crud;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

interface AppointmentRepository extends Repository<Appointment, Long> {

    Optional<Appointment> findById(Long id);

    Set<Appointment> findAll();

    Appointment save(Appointment appointment);

    @Modifying
    @Transactional
    @Query("DELETE FROM Appointment a WHERE a.id = :id")
    void deleteById(@Param("id") Long id);
}
