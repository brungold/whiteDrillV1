package pl.whitedrillv1.domain.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
class PatientDeleter {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final AddressRepository addressRepository;
}
