package mk.ukim.finki.wp.kol2025g3.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.kol2025g3.model.Vendor;
import mk.ukim.finki.wp.kol2025g3.model.exceptions.InvalidVendorIdException;
import mk.ukim.finki.wp.kol2025g3.repository.VendorRepository;
import mk.ukim.finki.wp.kol2025g3.service.VendorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;

    @Override
    public Vendor findById(Long id) {
        return vendorRepository.findById(id).orElseThrow(()-> new InvalidVendorIdException(id));
    }

    @Override
    public List<Vendor> listAll() {
        return vendorRepository.findAll();
    }

    @Override
    public Vendor create(String name) {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Vendor vendor = new Vendor(name);
        return vendorRepository.save(vendor);
    }
}
