package pl.whitedrillv1.domain.crud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.whitedrillv1.domain.crud.dto.PatientDto;
import pl.whitedrillv1.domain.crud.dto.PatientRequestDto;

@Service
@AllArgsConstructor
public class ClinicCrudFacade {

    private final PatientAdder patientAdder;
    private final PatientRetriever patientRetriever;

    public PatientDto addPatient(PatientRequestDto dto) {
        return  patientAdder.addPatient(dto);
    }



    public PatientDto findPatientDtoById(Long id) {
        return patientRetriever.findPatientDtoById(id);
    }


}
