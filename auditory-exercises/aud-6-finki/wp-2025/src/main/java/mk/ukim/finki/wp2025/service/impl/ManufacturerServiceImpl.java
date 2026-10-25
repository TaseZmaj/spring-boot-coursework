package mk.ukim.finki.wp2025.service.impl;

import mk.ukim.finki.wp2025.model.Manufacturer;
import mk.ukim.finki.wp2025.model.exceptions.ManufacturerNotFoundException;
import mk.ukim.finki.wp2025.repository.jpa.ManufacturerRepository;
import mk.ukim.finki.wp2025.repository.mock.InMemoryManufacturerRepository;
import mk.ukim.finki.wp2025.service.ManufacturerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Manufacturer> listManufacturers() {
        return manufacturerRepository.findAll();
    }

    @Override
    public Manufacturer findById(Long id) {
        return manufacturerRepository.findById(id).orElseThrow(() -> new ManufacturerNotFoundException(id));
    }

    @Override
    public Manufacturer create(String name, String address) {
        if (name == null || name.isEmpty() || address == null || address.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return manufacturerRepository.save(new Manufacturer(name, address));
    }

    @Override
    public Manufacturer update(Long id, String name, String address) {
        if (name == null || name.isEmpty() || address == null || address.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Manufacturer manufacturer = manufacturerRepository.findById(id)
                                                          .orElseThrow(() -> new ManufacturerNotFoundException(id));

        manufacturer.setName(name);
        manufacturer.setAddress(address);
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public void delete(Long id) {
        manufacturerRepository.deleteById(id);
    }

    @Override
    public List<Manufacturer> searchManufacturers(String text) {
        return manufacturerRepository.findAll();
    }

}
