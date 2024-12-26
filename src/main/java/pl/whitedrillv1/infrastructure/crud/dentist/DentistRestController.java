package pl.whitedrillv1.infrastructure.crud.dentist;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.whitedrillv1.domain.crud.ClinicCrudFacade;
import pl.whitedrillv1.domain.crud.dto.DentistDto;

@RestController
@Log4j2
@RequestMapping("/dentist")
@AllArgsConstructor
class DentistRestController {

    private final ClinicCrudFacade clinicCrudFacade;

    @GetMapping("/{dentistId}")
    ResponseEntity<GetDentistResponseDto> getDentistById(@PathVariable Long dentistId) {
        DentistDto dentistDtoById = clinicCrudFacade.findDentistDtoById(dentistId);
        GetDentistResponseDto response = new GetDentistResponseDto(dentistDtoById);
        return ResponseEntity.ok(response);
    }
}
