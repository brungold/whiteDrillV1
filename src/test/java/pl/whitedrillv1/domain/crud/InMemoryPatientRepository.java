package pl.whitedrillv1.domain.crud;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

class InMemoryPatientRepository implements PatientRepository {

    Map<Long, Patient> db = new HashMap<>();
    AtomicInteger index = new AtomicInteger(0);

    @Override
    public Optional<Patient> findById(final Long id) {
        Patient patient = db.get(id);
        return Optional.ofNullable(patient);
    }

    @Override
    public Patient save(Patient patient) {
        long index = this.index.getAndIncrement();
        db.put(index, patient);
        patient.setId(index);
        return patient;
    }

    @Override
    public Set<Patient> findAll() {
        return new HashSet<>(db.values());
    }

    @Override
    public void deleteById(final Long id) {
        db.remove(id);
    }
}
