package pl.whitedrillv1.domain.crud;

class ScheduleAlreadyExistsException extends RuntimeException {
    public ScheduleAlreadyExistsException(String message) {
        super(message);
    }
}
