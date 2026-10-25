package mk.ukim.finki.wp.kol2025g2.service;

import mk.ukim.finki.wp.kol2025g2.model.SkiResort;
import mk.ukim.finki.wp.kol2025g2.model.exceptions.InvalidSkiResortIdException;

import java.util.List;

public interface SkiResortService {

    /**
     * @param id The id of the ski resort that we want to obtain
     * @return The ski resort with the appropriate id
     * @throws InvalidSkiResortIdException when there is no ski resort with the given id
     */
    SkiResort findById(Long id);

    /**
     * @return List of all ski resorts in the database
     */
    List<SkiResort> listAll();

    /**
     * This method is used to create a new ski resort, and save it in the database.
     *
     * @param name     The name of the ski resort
     * @param location The location of the ski resort
     * @return The ski resort that is created. The id should be generated when the ski resort is created.
     */
    SkiResort create(String name, String location);
}
