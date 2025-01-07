package pl.whitedrillv1.domain.crud;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

class InMemoryAddressRepository implements AddressRepository {

    Map<Long, Address> db = new HashMap<>();
    AtomicInteger index = new AtomicInteger(0);

    @Override
    public Optional<Address> findById(final Long id) {
        Address address = db.get(id);
        return Optional.ofNullable(address);
    }

    @Override
    public Address save(final Address address) {
        long id = this.index.getAndIncrement();
        address.setId(id);
        db.put(id, address);
        return address;
    }

    @Override
    public void deleteById(final Long id) {
        db.remove(id);
    }
}
