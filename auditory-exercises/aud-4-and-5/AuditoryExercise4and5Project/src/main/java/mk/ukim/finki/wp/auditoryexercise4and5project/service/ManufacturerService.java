package mk.ukim.finki.wp.auditoryexercise4and5project.service;

import mk.ukim.finki.wp.auditoryexercise4and5project.model.Manufacturer;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.Product;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.enums.ProductLevel;

import java.util.List;

public interface ManufacturerService{
    Manufacturer create(
            String name,
            String address
    );

    Manufacturer update(
            Long id,
            String name,
            String address
    );

    List<Manufacturer> findAll();

    List<Manufacturer> search(String text);

    Manufacturer findById(Long id);

    void delete(Long id);
}
