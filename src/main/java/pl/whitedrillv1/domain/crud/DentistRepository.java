package pl.whitedrillv1.domain.crud;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

interface DentistRepository extends Repository<Dentist, Long> {

    @Query("SELECT d FROM Dentist d WHERE d.id = :id")
    Optional<Dentist> findById(Long id);

    Dentist save(Dentist dentist);

}
