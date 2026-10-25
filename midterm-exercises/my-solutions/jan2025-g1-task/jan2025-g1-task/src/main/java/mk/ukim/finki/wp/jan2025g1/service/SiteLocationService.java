package mk.ukim.finki.wp.jan2025g1.service;

import mk.ukim.finki.wp.jan2025g1.model.SiteLocation;
import mk.ukim.finki.wp.jan2025g1.model.exceptions.InvalidSiteLocationIdException;

import java.util.List;

public interface SiteLocationService {

    /**
     * @param id The id of the location that we want to obtain
     * @return The location with the appropriate id
     * @throws InvalidSiteLocationIdException when there is no location with the given id
     */
    SiteLocation findById(Long id);

    /**
     * @return List of all locations in the database
     */
    List<SiteLocation> listAll();

    /**
     * This method is used to create a new location and save it in the database.
     *
     * @param city    The city of the location
     * @param country The country of the location
     * @return The location that is created. The id should be generated when the location is created.
     */
    SiteLocation create(String city, String country);
}
