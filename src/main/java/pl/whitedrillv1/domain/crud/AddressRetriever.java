package pl.whitedrillv1.domain.crud;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.whitedrillv1.domain.crud.dto.AddressDto;

@Service
@Log4j2
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AddressRetriever {

    private final AddressRepository addressRepository;

    AddressDto findAddressDtoById(Long id) {
        return addressRepository.findById(id)
                .map(AddressMapper::mapFromAddressToAddressDto)
                .orElseThrow(() -> new AddressNotFoundException("Addressu o podanym id " + id + " nie znaleziono"));
    }

    Address findAddressById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Addressu o podanym id " + id + " nie znaleziono"));
    }
}
