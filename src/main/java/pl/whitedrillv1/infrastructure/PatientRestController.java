package pl.whitedrillv1.infrastructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.whitedrillv1.domain.crud.ClinicCrudFacade;
import pl.whitedrillv1.domain.crud.dto.PatientRequestDto;

@RestController
@Log4j2
@RequestMapping("/patients")
@AllArgsConstructor
public class PatientRestController {

    private final ClinicCrudFacade clinicCrudFacade;

//    @PostMapping
//    ResponseEntity<CreatePatientResponseDto> postPatient(@RequestBody @Valid PatientRequestDto patientRequestDto) {
//
//    }
}
