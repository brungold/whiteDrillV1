package pl.whitedrillv1.domain.crud.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalTime;

@Builder
public record AppointmentBasicUpdateDto(
        LocalTime appointmentTime,
        Integer duration,
        BigDecimal price
) {
}