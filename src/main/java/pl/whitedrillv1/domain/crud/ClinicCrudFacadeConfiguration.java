package pl.whitedrillv1.domain.crud;

class ClinicCrudFacadeConfiguration {

    public static ClinicCrudFacade createClinicCrudFacade(
            final PatientRepository patientRepository) {
        PatientAdder patientAdder = new PatientAdder(patientRepository);
        PatientRetriever patientRetriever = new PatientRetriever(patientRepository);
        return new ClinicCrudFacade(
                patientAdder,
                patientRetriever);
    }
}
