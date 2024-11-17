package pl.whitedrillv1.domain.crud.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

@Builder
public record PatientRequestDto(
        @NotBlank(message = "First name cannot be blank")
        @Size(max = 50, message = "First name cannot exceed 50 characters")
        String firstName,

        @NotBlank(message = "First name cannot be blank")
        @Size(max = 50, message = "First name cannot exceed 50 characters")
        String lastName,

        @NotNull(message = "Birth date cannot be null")
        LocalDate birthDate,

        @NotNull(message = "Phone number cannot be null")
        String phone,

        @Email(message = "Invalid email format")
        String email,

        @NotNull(message = "Gender is required")
        PatientGenderDto patientGender,

        @Pattern(
                regexp = "\\d{11}",
                message = "PESEL should contain exactly 11 digits"
        )
        String pesel,

        String maidenName,
        String language,
        String nationality,
        String nip,
        AddressDto addressDto
) {
}
