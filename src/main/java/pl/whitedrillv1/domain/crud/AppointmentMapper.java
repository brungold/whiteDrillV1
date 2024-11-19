package pl.whitedrillv1.domain.crud;

import pl.whitedrillv1.domain.crud.dto.AppointmentDto;

class AppointmentMapper {

    public static AppointmentDto mapToAppointmentDto(Appointment appointment) {
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
}
