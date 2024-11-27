package pl.whitedrillv1.domain.crud;

class ScheduleInvalidTimeRangeException extends RuntimeException {
    public ScheduleInvalidTimeRangeException(String message) {
        super(message);
    }
}
