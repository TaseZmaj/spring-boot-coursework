package mk.ukim.finki.wp.jan2025g1.service;

import mk.ukim.finki.wp.jan2025g1.model.ArchaeologicalSite;
import mk.ukim.finki.wp.jan2025g1.model.HistoricalPeriod;
import mk.ukim.finki.wp.jan2025g1.model.exceptions.InvalidArchaeologicalSiteIdException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ArchaeologicalSiteService {

    /**
     * @return List of all archaeological sites in the database
     */
    List<ArchaeologicalSite> listAll();

    /**
     * @param id The id of the archaeological site that we want to obtain
     * @return The archaeological site with the appropriate id
     * @throws InvalidArchaeologicalSiteIdException when there is no archaeological site with the given id
     */
    ArchaeologicalSite findById(Long id);

    /**
     * This method is used to create a new archaeological site and save it in the database.
     *
     * @param name       The name of the archaeological site
     * @param areaSize   The area size of the archaeological site
     * @param rating     The rating of the archaeological site
     * @param period     The historical period associated with the archaeological site
     * @param locationId The id of the location where the archaeological site is found
     * @return The archaeological site that is created. The id should be generated when the archaeological site is created.
     */
    ArchaeologicalSite create(String name, Double areaSize, Double rating, HistoricalPeriod period, Long locationId);

    /**
     * This method is used to update an archaeological site and save it in the database.
     *
     * @param id         The id of the archaeological site that is being updated
     * @param name       The name of the archaeological site
     * @param areaSize   The area size of the archaeological site
     * @param rating     The rating of the archaeological site
     * @param period     The historical period associated with the archaeological site
     * @param locationId The id of the location of the archaeological site
     * @return The archaeological site that is updated.
     * @throws InvalidArchaeologicalSiteIdException when there is no archaeological site with the given id
     */
    ArchaeologicalSite update(Long id, String name, Double areaSize, Double rating, HistoricalPeriod period, Long locationId);

    /**
     * This method deletes an archaeological site from the database.
     *
     * @param id The id of the archaeological site that we want to delete
     * @return The archaeological site that is deleted.
     * @throws InvalidArchaeologicalSiteIdException when there is no archaeological site with the given id
     */
    ArchaeologicalSite delete(Long id);

    /**
     * This method closes an archaeological site.
     *
     * @param id The ID of the archaeological site we want to close.
     * @return The updated archaeological site.
     * @throws InvalidArchaeologicalSiteIdException If the archaeological site with the given ID does not exist.
     */
    ArchaeologicalSite close(Long id);

    /**
     * Returns a page of archaeological sites that match the given criteria.
     *
     * @param name     Filters archaeological sites whose names contain the specified text.
     * @param areaSize Filters archaeological sites bigger than the specified area size.
     * @param rating   Filters archaeological sites with a rating greater than the specified value.
     * @param period   Filters archaeological sites based on the historical period.
     * @param locationId Filters archaeological sites by the specified locationId.
     * @param pageNum  The page number.
     * @param pageSize The number of items per page.
     * @return The page of archaeological sites that match the given criteria.
     */
    Page<ArchaeologicalSite> findPage(String name, Double areaSize, Double rating, HistoricalPeriod period, Long locationId, int pageNum, int pageSize);
}
