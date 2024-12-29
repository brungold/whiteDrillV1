package pl.whitedrillv1.infrastructure.crud.appointment;

import pl.whitedrillv1.domain.crud.dto.AppointmentDto;
import pl.whitedrillv1.infrastructure.crud.appointment.dto.BasicUpdateAppointmentResponseDto;

public class AppointmentControllerMapper {

    static BasicUpdateAppointmentResponseDto mapAppointmentDtoToBasicUpdateAppointmentResponseDto(AppointmentDto appointmentDto) {
        return new BasicUpdateAppointmentResponseDto(
                appointmentDto.appointmentTime(),
                appointmentDto.duration(),
                appointmentDto.price()
        );
    }
}
