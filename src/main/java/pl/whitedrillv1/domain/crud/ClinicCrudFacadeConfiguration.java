package pl.whitedrillv1.domain.crud;

class ClinicCrudFacadeConfiguration {

    public static ClinicCrudFacade createClinicCrudFacade(
            final PatientRepository patientRepository,
            final ScheduleRepository scheduleRepository,
            final DentistRepository dentistRepository,
            final AppointmentRepository appointmentRepository
    ) {
        PatientAdder patientAdder = new PatientAdder(patientRepository);
        PatientRetriever patientRetriever = new PatientRetriever(patientRepository);
        DentistRetriever dentistRetriever = new DentistRetriever(dentistRepository);
        AppointmentRetriever appointmentRetriever = new AppointmentRetriever(appointmentRepository);
        DentistAssigner dentistAssigner = new DentistAssigner(dentistRetriever, appointmentRetriever);
        ScheduleAdder scheduleAdder = new ScheduleAdder(dentistAssigner, scheduleRepository);
        return new ClinicCrudFacade(
                patientAdder,
                patientRetriever,
                scheduleAdder
        );
    }
}
