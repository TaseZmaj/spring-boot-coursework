package mk.ukim.finki.wp.auditoryexercise4and5project.service.implementations;

import mk.ukim.finki.wp.auditoryexercise4and5project.model.Manufacturer;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.exceptions.ManufacturerNotFoundException;
import mk.ukim.finki.wp.auditoryexercise4and5project.repository.ManufacturerRepository;
import mk.ukim.finki.wp.auditoryexercise4and5project.service.ManufacturerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public Manufacturer create(String name, String address) {
        if(name == null || name.isEmpty() || address == null || address.isEmpty()){
            throw new IllegalArgumentException();
        }
        return manufacturerRepository.save(new Manufacturer(name, address));
    }

    @Override
    public Manufacturer update(Long id, String name, String address) {
        if(name == null || name.isEmpty() || address == null || address.isEmpty()){
            throw new IllegalArgumentException();
        }
        Manufacturer manufacturer = findById(id);

        manufacturer.setName(name);
        manufacturer.setAddress(address);

        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public List<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }

    @Override
    public List<Manufacturer> search(String text) {
        return manufacturerRepository.search(text);
    }

    @Override
    public Manufacturer findById(Long id) {
        return manufacturerRepository.findById(id).orElseThrow(()-> new ManufacturerNotFoundException(id));
    }

    @Override
    public void delete(Long id) {
        manufacturerRepository.delete(id);
    }
}
