package pl.whitedrillv1.domain.crud;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

class InMemoryDentistRepository implements DentistRepository {

    Map<Long, Dentist> db = new HashMap<>();
    AtomicInteger index = new AtomicInteger(1);

    public InMemoryDentistRepository() {
        Dentist defaultDentist = new Dentist(1L, "Oskar", "Test");
        db.put(defaultDentist.getId(), defaultDentist);
    }


    @Override
    public Optional<Dentist> findById(Long id) {
        Dentist dentist = db.get(id);
        return Optional.ofNullable(dentist);
    }

    @Override
    public Dentist save(Dentist dentist) {
        long id = this.index.getAndIncrement();
        db.put(id, dentist);
        dentist.setId(id);
        return dentist;
    }
}
