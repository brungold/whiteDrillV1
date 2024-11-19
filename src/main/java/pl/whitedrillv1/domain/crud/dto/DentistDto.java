package pl.whitedrillv1.domain.crud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record DentistDto(
        Long id, // Identyfikator dentysty

        @NotBlank(message = "Dentist's first name cannot be blank")
        @Size(max = 50, message = "Dentist's first name must not exceed 50 characters")
        String firstName,

        @NotBlank(message = "Dentist's last name cannot be blank")
        @Size(max = 50, message = "Dentist's last name must not exceed 50 characters")
        String lastName
) {
}
