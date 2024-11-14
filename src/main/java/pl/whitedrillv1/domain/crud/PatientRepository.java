package pl.whitedrillv1.domain.crud;

import org.springframework.data.repository.Repository;

import java.util.List;

interface PatientRepository extends Repository<Patient, Long> {

    Patient findById(long id);

    Patient save(Patient patient);

    List<Patient> findAll();

}
