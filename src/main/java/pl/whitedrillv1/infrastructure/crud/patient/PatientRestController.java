package pl.whitedrillv1.infrastructure.crud.patient;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.whitedrillv1.domain.crud.ClinicCrudFacade;
import pl.whitedrillv1.domain.crud.dto.PatientDto;
import pl.whitedrillv1.domain.crud.dto.PatientRequestDto;
import pl.whitedrillv1.infrastructure.crud.patient.dto.CreatePatientResponseDto;
import pl.whitedrillv1.infrastructure.crud.patient.dto.DeletePatientResponseDto;
import pl.whitedrillv1.infrastructure.crud.patient.dto.GetAllPatientsResponseDto;
import pl.whitedrillv1.infrastructure.crud.patient.dto.GetPatientResponseDto;

import java.util.Set;

import static pl.whitedrillv1.infrastructure.crud.patient.PatientMapper.mapFromPatientToDeletePatientResponseDto;

@RestController
@Log4j2
@RequestMapping("/patients")
@AllArgsConstructor
public class PatientRestController {

    private final ClinicCrudFacade clinicCrudFacade;

    @GetMapping("/{id}")
    ResponseEntity<GetPatientResponseDto> getPatientById(@PathVariable Long id) {
        log.info("Received GET request for getPatientById: {}", id);
        PatientDto patientDto = clinicCrudFacade.findPatientDtoById(id);
        GetPatientResponseDto response = new GetPatientResponseDto(patientDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    ResponseEntity<GetAllPatientsResponseDto> getAllPatients(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        log.info("Received GET request for getAllPatients");
        Set<PatientDto> allPatients = clinicCrudFacade.findAllPatients();
        GetAllPatientsResponseDto response = new GetAllPatientsResponseDto(allPatients);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    ResponseEntity<CreatePatientResponseDto> postPatient(@RequestBody @Valid PatientRequestDto patientRequestDto) {
        log.info("Received POST request to create a new patient: name={} {}",
                patientRequestDto.firstName(), patientRequestDto.lastName());

        PatientDto savedPatient = clinicCrudFacade.addPatient(patientRequestDto);
        CreatePatientResponseDto createPatientResponseDto = new CreatePatientResponseDto(savedPatient);
        return ResponseEntity.ok(createPatientResponseDto);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<DeletePatientResponseDto> deletePatientById(@PathVariable Long id) {
        log.info("Received DELETE request for deletePatientById: {}", id);
        clinicCrudFacade.deletePatientById(id);
        DeletePatientResponseDto response = mapFromPatientToDeletePatientResponseDto(id);
        log.info("Deleted Patient with id: {} by deletePatientById.", id);
        return ResponseEntity.ok(response);
    }
}
