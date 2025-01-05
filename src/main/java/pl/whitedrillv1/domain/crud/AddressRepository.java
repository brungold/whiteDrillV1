package pl.whitedrillv1.domain.crud;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface AddressRepository extends Repository<Address, Long> {

    Optional<Address> findById(Long id);

    Address save(Address address);
}
