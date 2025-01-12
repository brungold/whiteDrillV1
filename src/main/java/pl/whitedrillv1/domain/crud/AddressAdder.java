package pl.whitedrillv1.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.whitedrillv1.domain.crud.dto.AddressDto;
import pl.whitedrillv1.domain.crud.dto.AddressRequestDto;

@Log4j2
@Service
@AllArgsConstructor
@Transactional
class AddressAdder {

    private final AddressRepository addressRepository;
    private final PatientRetriever patientRetriever;

    AddressDto addAddressToPatient(Long patientId, AddressRequestDto addressRequestDto) {
        // Znajdź pacjenta
        Patient patient = patientRetriever.findPatientById(patientId);

        // Tworzenie encji Address na podstawie AddressDto
        Address address = Address.builder()
                .postalCode(addressRequestDto.postalCode())
                .city(addressRequestDto.city())
                .street(addressRequestDto.street())
                .houseNumber(addressRequestDto.houseNumber())
                .apartmentNumber(addressRequestDto.apartmentNumber())
                .build();

        patient.setAddress(address);

        log.info("Dodawanie adresu: {}", address);

        // Zapis adresu w bazie danych
        Address savedAddress = addressRepository.save(address);

        // Mapowanie encji Address na AddressDto i zwrócenie wyniku
        return AddressMapper.mapFromAddressToAddressDto(savedAddress);
    }
}
