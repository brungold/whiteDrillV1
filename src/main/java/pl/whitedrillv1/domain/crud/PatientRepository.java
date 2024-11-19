package pl.whitedrillv1.domain.crud;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;


import java.util.Optional;
import java.util.Set;

interface PatientRepository extends Repository<Patient, Long> {

    @Query("SELECT p FROM Patient p WHERE p.id = :id")
    Optional<Patient> findById(Long id);

    Patient save(Patient patient);

    Set<Patient> findAll();
}
