package pl.whitedrillv1.domain.crud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record AddressRequestDto(
        @Pattern(
                regexp = "\\d{2}-\\d{3}",
                message = "Postal code must match the format XX-XXX"
        )
        String postalCode,
        @NotBlank(message = "City cannot be blank")
        String city,
        @NotBlank(message = "Street cannot be blank")
        String street,
        @NotBlank(message = "House number cannot be blank")
        String houseNumber,
        String apartmentNumber
) {
}
