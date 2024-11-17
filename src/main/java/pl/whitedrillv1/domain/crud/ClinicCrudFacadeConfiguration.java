package pl.whitedrillv1.domain.crud;

class ClinicCrudFacadeConfiguration {

    public static ClinicCrudFacade createClinicCrudFacade(
            final PatientRepository patientRepository,
            final AppointmentRepository appointmentRepository
    ) {
        PatientAdder patientAdder = new PatientAdder(patientRepository);
        PatientRetriever patientRetriever = new PatientRetriever(patientRepository);
        AppointmentRetriever appointmentRetriever = new AppointmentRetriever(appointmentRepository);
        return new ClinicCrudFacade(
                patientAdder,
                patientRetriever,
                appointmentRetriever
        );
    }
}
