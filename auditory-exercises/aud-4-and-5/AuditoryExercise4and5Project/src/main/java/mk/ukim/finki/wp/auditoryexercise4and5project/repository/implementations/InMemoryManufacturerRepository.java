package mk.ukim.finki.wp.auditoryexercise4and5project.repository.implementations;

import mk.ukim.finki.wp.auditoryexercise4and5project.bootstrap.DataHolder;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.Manufacturer;
import mk.ukim.finki.wp.auditoryexercise4and5project.repository.ManufacturerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryManufacturerRepository implements ManufacturerRepository {
    //CRUD - READ
    @Override
    public Optional<Manufacturer> findById(long id) {
        return DataHolder.manufacturers.stream().filter(
                m -> m.getId() == id)
                .findFirst();
    }

    //CRUD - CREATE or UPDATE
    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        DataHolder.manufacturers.removeIf(
                m -> m.getName().equals(manufacturer.getName())||
                            m.getId() == manufacturer.getId()
        );
        DataHolder.manufacturers.add(manufacturer);
        return manufacturer;
    }

    //CRUD - READ
    @Override
    public List<Manufacturer> findAll() {
        return DataHolder.manufacturers;
    }

    @Override
    public List<Manufacturer> search(String text) {
        String searchQuery = text.toLowerCase();
        return DataHolder.manufacturers.stream().filter(
                m -> m.getName().toLowerCase().contains(searchQuery) ||
                        m.getAddress().toLowerCase().contains(searchQuery) ||
                        (String.valueOf(m.getId())).toLowerCase().contains(searchQuery)
        ).toList();
    }

    //CRUD - DELETE
    @Override
    public void delete(Long id) {
        DataHolder.manufacturers.removeIf(m->m.getId()==id);
    }
}
