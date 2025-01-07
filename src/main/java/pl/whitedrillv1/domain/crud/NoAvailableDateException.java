package pl.whitedrillv1.domain.crud;

class NoAvailableDateException extends RuntimeException {
    public NoAvailableDateException(final String message) {
        super(message);
    }
}
