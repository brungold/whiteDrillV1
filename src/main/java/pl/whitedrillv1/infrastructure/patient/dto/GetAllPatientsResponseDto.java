package pl.whitedrillv1.infrastructure.patient.dto;

import pl.whitedrillv1.domain.crud.dto.PatientDto;

import java.util.Set;

public record GetAllPatientsResponseDto(Set<PatientDto> patients) {
}
