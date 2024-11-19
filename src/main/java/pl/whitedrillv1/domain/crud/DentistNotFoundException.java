package pl.whitedrillv1.domain.crud;

class DentistNotFoundException extends RuntimeException {
    public DentistNotFoundException(String message) {
        super(message);
    }
}
