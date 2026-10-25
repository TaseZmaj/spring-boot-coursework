package mk.ukim.finki.wp.jan2025g2.service;

import mk.ukim.finki.wp.jan2025g2.model.NationalPark;
import mk.ukim.finki.wp.jan2025g2.model.ParkType;
import mk.ukim.finki.wp.jan2025g2.model.exceptions.InvalidNationalParkIdException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NationalParkService {

    /**
     * @return List of all national parks in the database
     */
    List<NationalPark> listAll();

    /**
     * @param id The id of the national park that we want to obtain
     * @return The national park with the appropriate id
     * @throws InvalidNationalParkIdException when there is no national park with the given id
     */
    NationalPark findById(Long id);

    /**
     * This method is used to create a new national park and save it in the database.
     *
     * @param name       The name of the national park
     * @param areaSize   The area size of the national park
     * @param rating     The rating of the national park
     * @param parkType   The park type associated with the national park
     * @param locationId The id of the location where the national park is found
     * @return The national park that is created. The id should be generated when the national park is created.
     */
    NationalPark create(String name, Double areaSize, Double rating, ParkType parkType, Long locationId);

    /**
     * This method is used to update a national park and save it in the database.
     *
     * @param id         The id of the national park that is being updated
     * @param name       The name of the national park
     * @param areaSize   The area size of the national park
     * @param rating     The rating of the national park
     * @param parkType   The park type associated with the national park
     * @param locationId The id of the location of the national park
     * @return The national park that is updated.
     * @throws InvalidNationalParkIdException when there is no national park with the given id
     */
    NationalPark update(Long id, String name, Double areaSize, Double rating, ParkType parkType, Long locationId);

    /**
     * This method deletes a national park from the database.
     *
     * @param id The id of the national park that we want to delete
     * @return The national park that is deleted.
     * @throws InvalidNationalParkIdException when there is no national park with the given id
     */
    NationalPark delete(Long id);

    /**
     * This method closes a national park.
     *
     * @param id The ID of the national park we want to close.
     * @return The updated national park.
     * @throws InvalidNationalParkIdException If the national park with the given ID does not exist.
     */
    NationalPark close(Long id);

    /**
     * Returns a page of national parks that match the given criteria.
     *
     * @param name       Filters national parks whose names contain the specified text.
     * @param areaSize   Filters national parks bigger than the specified area size.
     * @param rating     Filters national parks with a rating greater than the specified value.
     * @param parkType   Filters national parks based on the park type.
     * @param locationId Filters national parks by the specified locationId.
     * @param pageNum    The page number.
     * @param pageSize   The number of items per page.
     * @return The page of national parks that match the given criteria.
     */
    Page<NationalPark> findPage(String name, Double areaSize, Double rating, ParkType parkType, Long locationId, int pageNum, int pageSize);
}
