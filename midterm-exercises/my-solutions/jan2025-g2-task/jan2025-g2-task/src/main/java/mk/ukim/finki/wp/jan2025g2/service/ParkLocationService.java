package mk.ukim.finki.wp.jan2025g2.service;

import mk.ukim.finki.wp.jan2025g2.model.ParkLocation;
import mk.ukim.finki.wp.jan2025g2.model.exceptions.InvalidParkLocationIdException;

import java.util.List;

public interface ParkLocationService {

    /**
     * @param id The id of the location that we want to obtain
     * @return The location with the appropriate id
     * @throws InvalidParkLocationIdException when there is no location with the given id
     */
    ParkLocation findById(Long id);

    /**
     * @return List of all locations in the database
     */
    List<ParkLocation> listAll();

    /**
     * This method is used to create a new location and save it in the database.
     *
     * @param country   The country of the location
     * @param continent The continent of the location
     * @return The location that is created. The id should be generated when the location is created.
     */
    ParkLocation create(String country, String continent);
}
