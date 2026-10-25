package mk.ukim.finki.wp.kol2025g2.service;

import mk.ukim.finki.wp.kol2025g2.model.SkiSlope;
import mk.ukim.finki.wp.kol2025g2.model.SlopeDifficulty;
import mk.ukim.finki.wp.kol2025g2.model.exceptions.InvalidSkiSlopeIdException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SkiSlopeService {

    /**
     * @return List of all ski slopes in the database
     */
    List<SkiSlope> listAll();

    /**
     * @param id The id of the ski slope that we want to obtain
     * @return The ski slope with the appropriate id
     * @throws InvalidSkiSlopeIdException when there is no ski slope with the given id
     */
    SkiSlope findById(Long id);

    /**
     * This method is used to create a new ski slope, and save it in the database.
     *
     * @param name       The name of the ski slope
     * @param length     The length of the ski slope in meters
     * @param difficulty The difficulty level of the ski slope
     * @param skiResort  The ski resort that the ski slope belongs to
     * @return The ski slope that is created. The id should be generated when the ski slope is created.
     */
    SkiSlope create(String name, Integer length, SlopeDifficulty difficulty, Long skiResort);

    /**
     * This method is used to update a ski slope, and save it in the database.
     *
     * @param id         The id of the ski slope that is being updated
     * @param name       The name of the ski slope
     * @param length     The length of the ski slope in meters
     * @param difficulty The difficulty level of the ski slope
     * @param skiResort  The id of ski resort that the ski slope belongs to
     * @return The ski slope that is updated.
     * @throws InvalidSkiSlopeIdException when there is no ski slope with the given id
     */
    SkiSlope update(Long id, String name, Integer length, SlopeDifficulty difficulty, Long skiResort);

    /**
     * This method deletes a ski slope from the database.
     *
     * @param id The id of the ski slope that we want to delete
     * @return The ski slope that is deleted.
     * @throws InvalidSkiSlopeIdException when there is no ski slope with the given id
     */
    SkiSlope delete(Long id);

    /**
     * This method closes a ski slope.
     *
     * @param id The ID of the ski slope we want to close.
     * @return The updated ski slope.
     * @throws InvalidSkiSlopeIdException If the ski slope with the given ID does not exist.
     */
    SkiSlope close(Long id);

    /**
     * Returns a page of ski slopes that match the given criteria.
     *
     * @param name       Filters ski slopes whose names contain the specified text.
     * @param length     Filters ski slopes longer than the specified length in meters.
     * @param difficulty Filters ski slopes matching the specified difficulty level.
     * @param skiResort  Filters ski slopes belonging to the specified ski center.
     * @param pageNum    The page number
     * @param pageSize   The number of items per page
     * @return The page of ski slopes that match the given criteria.
     */
    Page<SkiSlope> findPage(String name, Integer length, SlopeDifficulty difficulty, Long skiResort, int pageNum, int pageSize);
}
