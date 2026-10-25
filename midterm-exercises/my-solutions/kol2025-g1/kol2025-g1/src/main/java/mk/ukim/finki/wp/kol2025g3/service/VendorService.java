package mk.ukim.finki.wp.kol2025g3.service;

import mk.ukim.finki.wp.kol2025g3.model.Vendor;
import mk.ukim.finki.wp.kol2025g3.model.exceptions.InvalidVendorIdException;

import java.util.List;

public interface VendorService {

    /**
     * @param id The id of the vendor that we want to obtain
     * @return The vendor with the appropriate id
     * @throws InvalidVendorIdException when there is no vendor with the given id
     */
    Vendor findById(Long id);

    /**
     * @return List of all vendors in the database
     */
    List<Vendor> listAll();

    /**
     * This method is used to create a new vendor, and save it in the database.
     *
     * @param name The name of the vendor
     * @return The vendor that is created. The id should be generated when the vendor is created.
     */
    Vendor create(String name);
}
