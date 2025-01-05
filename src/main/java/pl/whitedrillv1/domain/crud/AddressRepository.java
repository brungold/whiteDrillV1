package pl.whitedrillv1.domain.crud;

import org.springframework.data.repository.Repository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

interface AddressRepository extends Repository<Address, Long> {

    Optional<Address> findById(Long id);

    Address save(Address address);

    @Modifying
    @Transactional
    @Query("DELETE FROM Address a WHERE a.id = :id")
    void deleteById(@Param("id") Long id); //
}
