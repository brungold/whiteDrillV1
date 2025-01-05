package pl.whitedrillv1.domain.crud;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@Transactional
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AddressDeleter {

    private final AddressRepository addressRepository;
    private final AddressRetriever addressRetriever;

    void deleteAddress(Long addressId) {
        // Usunięcie adresu z bazy danych na podstawie ID
        addressRepository.deleteById(addressId);

        log.info("Adres o ID {} został usunięty.", addressId);
    }
}