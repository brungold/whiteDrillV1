package pl.whitedrillv1.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import pl.whitedrillv1.domain.crud.dto.DentistDto;

@Log4j2
@Service
@AllArgsConstructor
class DentistRetriever {

    private final DentistRepository dentistRepository;

    Dentist findDentistById(Long dentistId) {
        return  dentistRepository
                .findById(dentistId)
                .orElseThrow(() -> new DentistNotFoundException("Dentist  with id: " + dentistId + " not found"));
    }

    DentistDto findDentistDtoById(Long dentistId) {
        return dentistRepository
                .findById(dentistId)
                .map(DentistMapper::mapFromDentistToDentistDto)
                .orElseThrow(() -> new DentistNotFoundException("Dentist  with id: " + dentistId + " not found"));
    }

    Dentist assignDentistOskarSchedule() {
        return dentistRepository.findById(1L)
                .orElseThrow(() -> new DentistNotFoundException("Dentist not found"));
    }
}
