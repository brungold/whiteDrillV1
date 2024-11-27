package pl.whitedrillv1.domain.crud;

class HoursAlreadyBookedException extends RuntimeException {
    public HoursAlreadyBookedException(String message) {
        super(message);
    }
}
