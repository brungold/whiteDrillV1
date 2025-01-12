package pl.whitedrillv1.domain.crud;


class ClinicCrudFacadeConfiguration {

    public static ClinicCrudFacade createClinicCrudFacade(
            final PatientRepository patientRepository,
            final ScheduleRepository scheduleRepository,
            final DentistRepository dentistRepository,
            final AppointmentRepository appointmentRepository,
            final AddressRepository addressRepository
    ) {
        PatientAdder patientAdder = new PatientAdder(patientRepository);
        PatientRetriever patientRetriever = new PatientRetriever(patientRepository);
        DentistRetriever dentistRetriever = new DentistRetriever(dentistRepository);
        AppointmentRetriever appointmentRetriever = new AppointmentRetriever(appointmentRepository, scheduleRepository);
        DentistAssigner dentistAssigner = new DentistAssigner(dentistRetriever, appointmentRetriever);
        ScheduleRetriever scheduleRetriever = new ScheduleRetriever(scheduleRepository);
        ScheduleAdder scheduleAdder = new ScheduleAdder(dentistAssigner, scheduleRetriever ,scheduleRepository);
        AppointmentAdder appointmentAdder = new AppointmentAdder(appointmentRepository, scheduleRetriever, patientRepository, dentistRepository);
        AppointmentUpdater appointmentUpdater = new AppointmentUpdater(appointmentRepository, appointmentRetriever, scheduleRepository, patientRepository);
        PatientDeleter patientDeleter = new PatientDeleter(patientRetriever, patientRepository, appointmentRetriever, addressRepository);
        AddressAdder addressAdder = new AddressAdder(addressRepository, patientRetriever);
        AddressDeleter addressDeleter = new AddressDeleter(addressRepository, patientRepository);
        return new ClinicCrudFacade(
                patientAdder,
                patientRetriever,
                scheduleAdder,
                scheduleRetriever,
                appointmentAdder,
                appointmentRetriever,
                dentistRetriever,
                appointmentUpdater,
                patientDeleter,
                addressAdder,
                addressDeleter
        );
    }
}
