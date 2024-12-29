package pl.whitedrillv1.domain.crud;

class DateAlreadyBookedException extends RuntimeException {
    public DateAlreadyBookedException(final String message) {
        super(message);
    }
}
