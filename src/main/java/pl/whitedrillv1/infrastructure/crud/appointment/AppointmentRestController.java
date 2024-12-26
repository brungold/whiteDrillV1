package pl.whitedrillv1.infrastructure.crud.appointment;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.whitedrillv1.domain.crud.ClinicCrudFacade;
import pl.whitedrillv1.domain.crud.dto.AppointmentDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentRequestDto;
import pl.whitedrillv1.infrastructure.crud.appointment.dto.CreateAppointmentResponseDto;
import pl.whitedrillv1.infrastructure.crud.appointment.dto.GetAppointmentDto;

@RestController
@Log4j2
@RequestMapping("/appointments")
@AllArgsConstructor
class AppointmentRestController {

    private final ClinicCrudFacade clinicCrudFacade;

    @GetMapping("/{id}")
    ResponseEntity<GetAppointmentDto> getAppointmentById(@PathVariable Long id) {
        log.info("Received GET request for getAppointmentById: {}", id);
        AppointmentDto appointmentDto = clinicCrudFacade.findAppointmentDtoById(id);
        GetAppointmentDto response = new GetAppointmentDto(appointmentDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    ResponseEntity<CreateAppointmentResponseDto> postPatient(@RequestBody @Valid AppointmentRequestDto appointmentRequestDto) {
        log.info("Received POST request to create a new appointment: " + appointmentRequestDto);
        AppointmentDto appointmentDto = clinicCrudFacade.addAppointment(appointmentRequestDto);
        CreateAppointmentResponseDto response = new CreateAppointmentResponseDto(appointmentDto);
        return ResponseEntity.ok(response);
    }

}
