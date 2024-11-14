package pl.whitedrillv1.domain.crud;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

class InMemoryPatientRepository implements PatientRepository {

    Map<Long, Patient> db = new HashMap<Long, Patient>();
    AtomicInteger index = new AtomicInteger(0);

    @Override
    public Patient findById(long id) {
        return null;
    }

    @Override
    public Patient save(Patient patient) {
        long index = this.index.getAndIncrement();
        db.put(index, patient);
        patient.setId(index);
        return patient;
    }

    @Override
    public List<Patient> findAll() {
        return List.of();
    }
}
