package mk.ukim.finki.wp.jan2025g1.web;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.jan2025g1.model.ArchaeologicalSite;
import mk.ukim.finki.wp.jan2025g1.model.HistoricalPeriod;
import mk.ukim.finki.wp.jan2025g1.service.ArchaeologicalSiteService;
import mk.ukim.finki.wp.jan2025g1.service.SiteLocationService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class ArchaeologicalSiteController {

    private final ArchaeologicalSiteService archaeologicalSiteService;
    private final SiteLocationService siteLocationService;
    /**
     * This method should use the "list.html" template to display all archaeological sites.
     * The method should be mapped on paths '/' and '/archaeological-sites'.
     * The arguments that this method takes are optional and can be 'null'.
     * The filtered archaeological sites that are the result of the call
     * findPage method from the ArchaeologicalSiteService should be displayed.
     * If you want to return a paginated result, you should also pass the page number and the page size as arguments.
     *
     * @param name       Filters archaeological sites whose names contain the specified text
     * @param areaSize   Filters by area size bigger than the specified value
     * @param rating     Filters by rating greater than the specified value
     * @param period     Filters by historical period
     * @param locationId Filters by location
     * @param pageNum    The page number
     * @param pageSize   The number of items per page
     * @return The view "list.html"
     */
    @GetMapping(path={"/", "/archaeological-sites"})
    public String listAll(@RequestParam(required=false)String name,
                          @RequestParam(required=false)Double areaSize,
                          @RequestParam(required=false)Double rating,
                          @RequestParam(required=false)HistoricalPeriod period,
                          @RequestParam(required=false)Long locationId,
                          @RequestParam(required=false, defaultValue = "1")Integer pageNum,
                          @RequestParam(required=false, defaultValue = "20")Integer pageSize,
                          Model model) {
        Page<ArchaeologicalSite> page = archaeologicalSiteService.findPage(name, areaSize, rating, period, locationId, pageNum - 1, pageSize);
        model.addAttribute("page", page);

        model.addAttribute("period", period);
        model.addAttribute("locationId", locationId);

        model.addAttribute("periods", HistoricalPeriod.values());
        model.addAttribute("locations", siteLocationService.listAll());

        return "list";
    }

    /**
     * This method should display the "form.html" template.
     * The method should be mapped on path '/archaeological-sites/add'.
     *
     * @return The view "form.html".
     */
    @GetMapping("/archaeological-sites/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAdd(Model model) {
        model.addAttribute("periods", HistoricalPeriod.values());
        model.addAttribute("locations", siteLocationService.listAll());
        return "form";
    }

    /**
     * This method should display the "form.html" template.
     * However, in this case, all 'input' elements should be filled with the appropriate value for the archaeological site that is updated.
     * The method should be mapped on path '/archaeological-sites/edit/[id]'.
     *
     * @return The view "form.html".
     */
    @GetMapping("/archaeological-sites/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showEdit(@PathVariable Long id, Model model) {
        model.addAttribute("archSite", archaeologicalSiteService.findById(id));

        model.addAttribute("periods", HistoricalPeriod.values());
        model.addAttribute("locations", siteLocationService.listAll());
        return "form";
    }

    /**
     * This method should create a new archaeological site given the arguments it takes.
     * The method should be mapped on path '/archaeological-sites'.
     * After the archaeological site is created, all archaeological sites should be displayed.
     *
     * @param name       The name of the archaeological site
     * @param areaSize   The area size
     * @param rating     The rating of the site
     * @param period     The historical period
     * @param locationId The location ID
     * @return Redirects to the list of archaeological sites
     */
    @PostMapping("/archaeological-sites")
    @PreAuthorize("hasRole('ADMIN')")
    public String create(@RequestParam String name,
                         @RequestParam Double areaSize,
                         @RequestParam Double rating,
                         @RequestParam HistoricalPeriod period,
                         @RequestParam Long locationId) {
        archaeologicalSiteService.create(name, areaSize, rating, period, locationId);
        return "redirect:/archaeological-sites";
    }

    /**
     * This method should update an existing archaeological site given the arguments it takes.
     * The method should be mapped on path '/archaeological-sites/[id]'.
     * After the archaeological site is updated, all archaeological sites should be displayed.
     *
     * @param id         The ID of the archaeological site to update
     * @param name       The name of the archaeological site
     * @param areaSize   The area size
     * @param rating     The rating of the site
     * @param period     The historical period
     * @param locationId The location ID
     * @return Redirects to the list of archaeological sites
     */
    @PostMapping("/archaeological-sites/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String update(@PathVariable Long id,
                         @RequestParam String name,
                         @RequestParam Double areaSize,
                         @RequestParam Double rating,
                         @RequestParam HistoricalPeriod period,
                         @RequestParam Long locationId) {
        archaeologicalSiteService.update(id, name, areaSize, rating, period, locationId);
        return "redirect:/archaeological-sites";
    }

    /**
     * This method should delete the archaeological site that has the appropriate identifier.
     * The method should be mapped on path '/archaeological-sites/delete/[id]'.
     * After the archaeological sites is deleted, all archaeological sites should be displayed.
     *
     * @param id The ID of the archaeological site to delete
     * @return Redirects to the list of archaeological sites
     */
    @PostMapping("/archaeological-sites/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
        archaeologicalSiteService.delete(id);
        return "redirect:/archaeological-sites";
    }

    /**
     * This method should close the archaeological sites that has the appropriate identifier.
     * The method should be mapped on path '/archaeological-sites/close/[id]'.
     * After the selected archaeological site is closed, all archaeological sites should be displayed.
     *
     * @param id The ID of the archaeological site to close
     * @return Redirects to the list of archaeological sites
     */
    @PostMapping("/archaeological-sites/close/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String close(@PathVariable Long id) {
        archaeologicalSiteService.close(id);
        return "redirect:/archaeological-sites";
    }
}

