package pl.whitedrillv1.domain.crud;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.Repository;


import java.util.Optional;
import java.util.Set;

interface PatientRepository extends Repository<Patient, Long> {

    @Query("SELECT p FROM Patient p WHERE p.id = :id")
    Optional<Patient> findById(Long id);

    Patient save(Patient patient);

    Set<Patient> findAll();

    @Modifying
    @Transactional
    @Query("DELETE FROM Patient p WHERE p.id = :id")
    void deleteById(@Param("id") Long id);
}
