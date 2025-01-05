package pl.whitedrillv1.domain.crud.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record AddressDto(
        @Pattern(
                regexp = "\\d{2}-\\d{3}",
                message = "Postal code must match the format XX-XXX"
        )
        String postalCode,

        String city,
        String street,
        String houseNumber,
        String apartmentNumber
) {
}
