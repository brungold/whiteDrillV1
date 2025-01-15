package pl.whitedrillv1.infrastructure.crud.appointment.dto;

import pl.whitedrillv1.domain.crud.dto.AppointmentDto;

import java.util.Set;

public record GetAllAppointmentsResponseDto(Set<AppointmentDto> appointments) {
}
