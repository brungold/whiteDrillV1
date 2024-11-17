package pl.whitedrillv1.domain.crud;

import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.Set;

interface AppointmentRepository extends Repository<Appointment, Long> {

    Optional<Appointment> findById(Long id);

    Set<Appointment> findAll();

    Appointment save(Appointment appointment);
}
