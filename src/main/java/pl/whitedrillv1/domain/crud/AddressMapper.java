package pl.whitedrillv1.domain.crud;

import pl.whitedrillv1.domain.crud.dto.AddressDto;

class AddressMapper {
    public static AddressDto mapFromAddressToAddressDto(Address address) {
        if (address == null) {
            return null;
        }

        return AddressDto.builder()
                .postalCode(address.getPostalCode())
                .city(address.getCity())
                .street(address.getStreet())
                .houseNumber(address.getHouseNumber())
                .apartmentNumber(address.getApartmentNumber())
                .build();
    }
}
