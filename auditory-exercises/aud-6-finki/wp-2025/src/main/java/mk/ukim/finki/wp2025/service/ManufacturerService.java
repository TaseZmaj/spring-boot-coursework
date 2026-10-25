package mk.ukim.finki.wp2025.service;

import mk.ukim.finki.wp2025.model.Manufacturer;

import java.util.List;

public interface ManufacturerService {
    List<Manufacturer> listManufacturers();

    Manufacturer findById(Long id);

    Manufacturer create(String name, String address);

    Manufacturer update(Long id, String name, String address);

    void delete(Long id);

    List<Manufacturer> searchManufacturers(String text);
}
