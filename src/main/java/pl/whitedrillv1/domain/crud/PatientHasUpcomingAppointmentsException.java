package pl.whitedrillv1.domain.crud;

class PatientHasUpcomingAppointmentsException extends RuntimeException {
    public PatientHasUpcomingAppointmentsException(String message) {
        super(message);
    }
}
