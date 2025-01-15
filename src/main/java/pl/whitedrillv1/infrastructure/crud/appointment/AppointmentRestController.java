package pl.whitedrillv1.infrastructure.crud.appointment;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.whitedrillv1.domain.crud.ClinicCrudFacade;
import pl.whitedrillv1.domain.crud.dto.AppointmentBasicUpdateDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentFirstAvailableHourDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentFullUpdateDto;
import pl.whitedrillv1.domain.crud.dto.AppointmentRequestDto;
import pl.whitedrillv1.infrastructure.crud.appointment.dto.BasicUpdateAppointmentResponseDto;
import pl.whitedrillv1.infrastructure.crud.appointment.dto.CreateAppointmentResponseDto;
import pl.whitedrillv1.infrastructure.crud.appointment.dto.FullUpdateAppointmentResponseDto;
import pl.whitedrillv1.infrastructure.crud.appointment.dto.GetAllAppointmentsResponseDto;
import pl.whitedrillv1.infrastructure.crud.appointment.dto.GetAppointmentDto;
import pl.whitedrillv1.infrastructure.crud.appointment.dto.GetFirstAvailableHoursResponseDto;

import java.util.Set;

import static pl.whitedrillv1.infrastructure.crud.appointment.AppointmentControllerMapper.mapAppointmentDtoToBasicUpdateAppointmentResponseDto;
import static pl.whitedrillv1.infrastructure.crud.appointment.AppointmentControllerMapper.mapAppointmentDtoToFullUpdateAppointmentResponseDto;
import static pl.whitedrillv1.infrastructure.crud.appointment.AppointmentControllerMapper.mapAppointmentsDtoToGetAllAppointmentsResponseDto;
import static pl.whitedrillv1.infrastructure.crud.appointment.AppointmentControllerMapper.mapFirstAvailableHoursDtoToGetFirstAvailableHoursResponseDto;

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

    @GetMapping
    ResponseEntity<GetAllAppointmentsResponseDto> getAppointments(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        log.info("Received GET request for getAppointments: {}", pageable);
        Set<AppointmentDto> allAppointmentsDto = clinicCrudFacade.findAllAppointmentsDto(pageable);
        GetAllAppointmentsResponseDto body = mapAppointmentsDtoToGetAllAppointmentsResponseDto(allAppointmentsDto);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{dentistId}")
    ResponseEntity<GetFirstAvailableHoursResponseDto> getFirstAvailableHours(@PathVariable Long dentistId) {
        log.info("Received GET request for getFirstAvailableHours for dentist with id: {}", dentistId);
        AppointmentFirstAvailableHourDto firstAvailableHours = clinicCrudFacade.findFirstAvailableHours(dentistId);
        GetFirstAvailableHoursResponseDto body = mapFirstAvailableHoursDtoToGetFirstAvailableHoursResponseDto(firstAvailableHours);
        log.info("Response body: {}", body);
        return ResponseEntity.ok(body);
    }

    @PostMapping
    ResponseEntity<CreateAppointmentResponseDto> postAppointment(@RequestBody @Valid AppointmentRequestDto appointmentRequestDto) {
        log.info("Received POST request to create a new appointment: " + appointmentRequestDto);
        AppointmentDto appointmentDto = clinicCrudFacade.addAppointment(appointmentRequestDto);
        CreateAppointmentResponseDto response = new CreateAppointmentResponseDto(appointmentDto);
        log.info("Added an appointment: {}", response);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    ResponseEntity<BasicUpdateAppointmentResponseDto> basicUpdateAppointment(@PathVariable Long id,
                                                                             @RequestBody AppointmentBasicUpdateDto request) {// @Valid Dto ??
        log.info("Received PATCH request basicUpdateAppointment to update an appointment: " + request);
        AppointmentDto appointmentDto = clinicCrudFacade.basicAppointmentUpdate(id, request);
        BasicUpdateAppointmentResponseDto body = mapAppointmentDtoToBasicUpdateAppointmentResponseDto(appointmentDto);
        log.info("Result for PATCH request " + request + " for basicUpdateAppointment to update an appointment is: " + body);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    ResponseEntity<FullUpdateAppointmentResponseDto> updateAppointment(@PathVariable Long id,
                                                                       @RequestBody AppointmentFullUpdateDto request) {// @Valid Dto ??)
        log.info("Received PUT request AppointmentFullUpdateDto to update an appointment: " + request);
        AppointmentDto appointmentDto = clinicCrudFacade.updateAppointment(id, request);
        FullUpdateAppointmentResponseDto body = mapAppointmentDtoToFullUpdateAppointmentResponseDto(appointmentDto);
        log.info("Result for PUT request " + request + " for AppointmentFullUpdateDto to update an appointment is: " + body);
        return ResponseEntity.ok(body);
    }
}