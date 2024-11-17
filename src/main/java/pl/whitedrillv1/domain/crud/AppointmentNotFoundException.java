package pl.whitedrillv1.domain.crud;

class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(final String message) {
        super(message);
    }
}
