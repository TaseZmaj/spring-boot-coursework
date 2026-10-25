package mk.ukim.finki.wp2025.repository.mock;

import mk.ukim.finki.wp2025.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface InMemoryManufacturerRepository {

    Optional<Manufacturer> findById(Long id);

    Manufacturer save(Manufacturer manufacturer);

    List<Manufacturer> findAll();

    List<Manufacturer> search(String text);

    void delete(Long id);
}
