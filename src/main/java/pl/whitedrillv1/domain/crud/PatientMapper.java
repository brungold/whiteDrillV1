package pl.whitedrillv1.domain.crud;

import pl.whitedrillv1.domain.crud.dto.AddressDto;
import pl.whitedrillv1.domain.crud.dto.PatientDto;

class PatientMapper {
    public static PatientDto mapFromPatientToPatientDto(Patient patient) {
        return PatientDto.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .gender(patient.getPatientGender().name())
                .birthDate(patient.getBirthDate())
                .pesel(patient.getPesel())
                .phone(patient.getPhone())
                .email(patient.getEmail())
                .address(AddressDto.builder()
                        .postalCode(patient.getAddress().getPostalCode())
                        .city(patient.getAddress().getCity())
                        .street(patient.getAddress().getStreet())
                        .houseNumber(patient.getAddress().getHouseNumber())
                        .apartmentNumber(patient.getAddress().getApartmentNumber())
                        .build())
                .build();
    }
}
