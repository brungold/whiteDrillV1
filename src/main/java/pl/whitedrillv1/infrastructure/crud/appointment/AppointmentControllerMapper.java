package pl.whitedrillv1.infrastructure.crud.appointment;

import pl.whitedrillv1.domain.crud.dto.AppointmentDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentFirstAvailableHourDto;
import pl.whitedrillv1.infrastructure.crud.appointment.dto.BasicUpdateAppointmentResponseDto;
import pl.whitedrillv1.infrastructure.crud.appointment.dto.FullUpdateAppointmentResponseDto;
import pl.whitedrillv1.infrastructure.crud.appointment.dto.GetAllAppointmentsResponseDto;
import pl.whitedrillv1.infrastructure.crud.appointment.dto.GetFirstAvailableHoursResponseDto;

import java.util.Set;

public class AppointmentControllerMapper {

    static BasicUpdateAppointmentResponseDto mapAppointmentDtoToBasicUpdateAppointmentResponseDto(AppointmentDto appointmentDto) {
        return new BasicUpdateAppointmentResponseDto(
                appointmentDto.appointmentDate(),
                appointmentDto.appointmentTime(),
                appointmentDto.duration(),
                appointmentDto.price()
        );
    }

    static FullUpdateAppointmentResponseDto mapAppointmentDtoToFullUpdateAppointmentResponseDto(AppointmentDto appointmentDto) {
        return new FullUpdateAppointmentResponseDto(
                appointmentDto.appointmentDate(),
                appointmentDto.appointmentTime(),
                appointmentDto.duration(),
                appointmentDto.patientId(),
                appointmentDto.dentistId(),
                appointmentDto.price(),
                appointmentDto.appointmentNotes()
        );
    }

    static GetAllAppointmentsResponseDto mapAppointmentsDtoToGetAllAppointmentsResponseDto(Set<AppointmentDto> appointmentsDto) {
        return new GetAllAppointmentsResponseDto(appointmentsDto);
    }

    static GetFirstAvailableHoursResponseDto mapFirstAvailableHoursDtoToGetFirstAvailableHoursResponseDto(AppointmentFirstAvailableHourDto dto) {
        return new GetFirstAvailableHoursResponseDto(
                dto.date(),
                dto.startHour()
        );
    }
}
