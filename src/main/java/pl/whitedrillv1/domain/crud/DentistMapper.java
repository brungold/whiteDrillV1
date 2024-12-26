package pl.whitedrillv1.domain.crud;

import pl.whitedrillv1.domain.crud.dto.DentistDto;

class DentistMapper {
    public static DentistDto mapFromDentistToDentistDto(Dentist dentist) {
        if (dentist == null) {
            return null;
        }
        return new DentistDto(
                dentist.getId(),
                dentist.getFirstName(),
                dentist.getLastName()
        );
    }

    public Dentist mapFromDentistDtoToDentist(DentistDto dentistDto) {
        if (dentistDto == null) {
            return null;
        }
        return new Dentist(
                dentistDto.id(),
                dentistDto.firstName(),
                dentistDto.lastName()
        );
    }
}