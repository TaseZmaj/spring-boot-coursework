package mk.ukim.finki.wp2025.repository.mock.impl;

import mk.ukim.finki.wp2025.bootstrap.DataHolder;
import mk.ukim.finki.wp2025.model.Manufacturer;
import mk.ukim.finki.wp2025.repository.mock.InMemoryManufacturerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryManufacturerRepositoryImpl implements InMemoryManufacturerRepository {

    @Override
    public Optional<Manufacturer> findById(Long id) {
        return DataHolder.manufacturers.stream().filter(manufacturer -> manufacturer.getId().equals(id)).findFirst();
    }

    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        delete(manufacturer.getId());
        DataHolder.manufacturers.add(manufacturer);
        return manufacturer;
    }

    @Override
    public List<Manufacturer> findAll() {
        return DataHolder.manufacturers;
    }

    @Override
    public List<Manufacturer> search(String text) {
        return DataHolder.manufacturers.stream()
                .filter(m ->
                        m.getName().toLowerCase().contains(text.toLowerCase()) ||
                                m.getAddress().toLowerCase().contains(text.toLowerCase()))
                .toList();
    }

    @Override
    public void delete(Long id) {
        DataHolder.manufacturers.removeIf(m -> m.getId().equals(id));
    }
}
