package pl.whitedrillv1.infrastructure.crud.patient.dto;

import org.springframework.http.HttpStatus;

public record DeletePatientResponseDto(String message, HttpStatus status) {
}
