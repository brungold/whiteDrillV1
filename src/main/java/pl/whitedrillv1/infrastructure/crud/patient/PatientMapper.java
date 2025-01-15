package pl.whitedrillv1.infrastructure.crud.patient;

import org.springframework.http.HttpStatus;
import pl.whitedrillv1.infrastructure.crud.patient.dto.DeletePatientResponseDto;

class PatientMapper {

    static DeletePatientResponseDto mapFromPatientToDeletePatientResponseDto(Long id){
        return new DeletePatientResponseDto("You deleted patient with id: " + id, HttpStatus.OK);
    }
}
