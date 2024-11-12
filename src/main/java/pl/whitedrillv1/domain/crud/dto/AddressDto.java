package pl.whitedrillv1.domain.crud.dto;

import lombok.Builder;

@Builder
public record AddressDto(
        int postalCode,
        String city,
        String street,
        int houseNumber,
        Integer apartmentNumber
) {
}
