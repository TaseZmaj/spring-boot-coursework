package mk.ukim.finki.wp.auditoryexercise4and5project.repository;

import mk.ukim.finki.wp.auditoryexercise4and5project.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerRepository {
    Optional<Manufacturer> findById(long id);
    Manufacturer save(Manufacturer manufacturer);
    List<Manufacturer> findAll();
    List<Manufacturer> search(String text);
    void delete(Long id);
}
