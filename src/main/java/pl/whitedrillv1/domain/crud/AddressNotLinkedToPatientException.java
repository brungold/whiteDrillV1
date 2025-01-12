package pl.whitedrillv1.domain.crud;

class AddressNotLinkedToPatientException extends RuntimeException {
    public AddressNotLinkedToPatientException(String message) {
        super(message);
    }
}
