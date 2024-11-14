package pl.whitedrillv1.domain.crud.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PatientRequestDto(
        String firstName,
        String lastName,
        LocalDate birthDate,
        String phone,
        String email,
        PatientGenderDto patientGender,
        String pesel,
        String maidenName,
        String language,
        String nationality,
        String nip,
        AddressDto addressDto
) {
}
