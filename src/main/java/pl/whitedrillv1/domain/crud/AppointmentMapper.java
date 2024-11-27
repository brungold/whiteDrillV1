package pl.whitedrillv1.domain.crud;

import pl.whitedrillv1.domain.crud.dto.AppointmentDto;

class AppointmentMapper {

    public static AppointmentDto mapFromAppointmentToAppointmentDto(Appointment appointment) {
        return AppointmentDto.builder()
                .id(appointment.getId())
                .appointmentDate(appointment.getAppointmentDate())
                .appointmentTime(appointment.getAppointmentTime())
                .duration(appointment.getDuration())
                .price(appointment.getPrice())
                .status(appointment.getStatus().name())
                .appointmentNotes(appointment.getAppointmentNotes())
                .patientId(appointment.getPatient().getId())
                .patientName(appointment.getPatient().getFullName())
                .dentistId(appointment.getDentist().getId())
                .dentistName(appointment.getDentist().getFullName())
                .build();
    }

    public static AppointmentDto toDto(Appointment appointment) {
        return AppointmentDto.builder()
                .id(appointment.getId())
                .appointmentDate(appointment.getAppointmentDate())
                .appointmentTime(appointment.getAppointmentTime())
                .duration(appointment.getDuration())
                .price(appointment.getPrice())
                .status(appointment.getStatus().name())
                .appointmentNotes(appointment.getAppointmentNotes())
                .dentistId(appointment.getDentist().getId())
                .patientId(appointment.getPatient().getId())
                .build();
    }
}
