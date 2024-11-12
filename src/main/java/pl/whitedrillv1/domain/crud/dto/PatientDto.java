package pl.whitedrillv1.domain.crud.dto;

import java.time.LocalDate;
import lombok.Builder;
import jakarta.validation.constraints.Pattern;

@Builder
public record PatientDto(
        String firstName,
        String lastName,
        String fullName,
        String gender,
        LocalDate birthDate,
        @Pattern(
                regexp = "\\d{11}",
                message = "PESEL should contain exactly 11 digits"
        )
        String pesel,
        @Pattern(
                regexp = "\\d{10}",
                message = "NIP should contain exactly 10 digits"
        )
        String phone,
        @Pattern(
                regexp = "\\+?\\d+",
                message = "Phone number should contain only digits, optionally starting with '+'"
        )
        String email,
        AddressDto address) {
}
