package pl.whitedrillv1.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.whitedrillv1.domain.crud.dto.AddressDto;

@Log4j2
@Service
@AllArgsConstructor
@Transactional
class AddressAdder {

    private final AddressRepository addressRepository;

    AddressDto addAddress(AddressDto addressDto) {
        // Tworzenie encji Address na podstawie AddressDto
        Address address = Address.builder()
                .postalCode(addressDto.postalCode())
                .city(addressDto.city())
                .street(addressDto.street())
                .houseNumber(addressDto.houseNumber())
                .apartmentNumber(addressDto.apartmentNumber())
                .build();

        log.info("Dodawanie adresu: {}", address);

        // Zapis adresu w bazie danych
        Address savedAddress = addressRepository.save(address);

        // Mapowanie encji Address na AddressDto i zwrócenie wyniku
        return AddressMapper.mapFromAddressToAddressDto(savedAddress);
    }
}
