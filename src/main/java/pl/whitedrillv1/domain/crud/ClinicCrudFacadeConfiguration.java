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
        ScheduleRetriever scheduleRetriever = new ScheduleRetriever(scheduleRepository);
        ScheduleAdder scheduleAdder = new ScheduleAdder(dentistAssigner, scheduleRetriever ,scheduleRepository);
        AppointmentAdder appointmentAdder = new AppointmentAdder(appointmentRepository, patientRepository, dentistRepository, scheduleRetriever);
        return new ClinicCrudFacade(
                patientAdder,
                patientRetriever,
                scheduleAdder,
                scheduleRetriever,
                appointmentAdder,
                appointmentRetriever
        );
    }
}
