package mk.ukim.finki.wp.kol2025g2.web;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.kol2025g2.model.SkiSlope;
import mk.ukim.finki.wp.kol2025g2.model.SlopeDifficulty;
import mk.ukim.finki.wp.kol2025g2.service.SkiResortService;
import mk.ukim.finki.wp.kol2025g2.service.SkiSlopeService;
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
public class SkiSlopeController {

    private final SkiResortService skiResortService;
    private final SkiSlopeService skiSlopeService;
    /**
     * This method should use the "list.html" template to display all ski slopes.
     * The method should be mapped on paths '/' and '/ski-slopes'.
     * The arguments that this method takes are optional and can be 'null'.
     * The filtered ski slopes that are the result of the call
     * findPage method from the SkiSlopeService should be displayed.
     * If you want to return a paginated result, you should also pass the page number and the page size as arguments.
     *
     * @param name       Filters ski slopes whose names contain the specified text.
     * @param length     Filters ski slopes longer than the specified length in meters.
     * @param difficulty Filters ski slopes matching the specified difficulty level.
     * @param skiResort  Filters ski slopes belonging to the specified ski center.
     * @param pageNum    The page number
     * @param pageSize   The number of items per page
     * @return The view "list.html".
     */

    @GetMapping(path={"/", "/ski-slopes"})
    public String listAll(@RequestParam(required = false) String name,
                          @RequestParam(required = false) Integer length,
                          @RequestParam(required = false) SlopeDifficulty difficulty,
                          @RequestParam(required = false) Long skiResort,
                          @RequestParam(defaultValue = "1", required = false) Integer pageNum,
                          @RequestParam(defaultValue = "10", required = false) Integer pageSize,
                          Model model) {
        Page<SkiSlope> page = skiSlopeService.findPage(name, length,difficulty, skiResort, pageNum - 1, pageSize);
        model.addAttribute("page", page);

//        model.addAttribute("name", name);
//        model.addAttribute("length", length);
//        model.addAttribute("difficulty", difficulty);
//        model.addAttribute("skiResort", skiResort);
//        model.addAttribute("pageNum", pageNum);
//        model.addAttribute("pageSize", pageSize);

        model.addAttribute("difficulties", SlopeDifficulty.values());
        model.addAttribute("skiResorts", skiResortService.listAll());
        return "list";
    }

    /**
     * This method should display the "form.html" template.
     * The method should be mapped on path '/ski-slopes/add'.
     *
     * @return The view "form.html".
     */
    @GetMapping("/ski-slopes/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAdd(Model model) {
        model.addAttribute("difficulties", SlopeDifficulty.values());
        model.addAttribute("skiResorts", skiResortService.listAll());
        return "form";
    }

    /**
     * This method should display the "form.html" template.
     * However, in this case, all 'input' elements should be filled with the appropriate value for the ski slope that is updated.
     * The method should be mapped on path '/ski-slopes/edit/[id]'.
     *
     * @return The view "form.html".
     */
    @GetMapping("/ski-slopes/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showEdit(@PathVariable Long id, Model model) {
        model.addAttribute("slope", skiSlopeService.findById(id));

        model.addAttribute("difficulties", SlopeDifficulty.values());
        model.addAttribute("skiResorts", skiResortService.listAll());
        return "form";
    }

    /**
     * This method should create a ski slope given the arguments it takes.
     * The method should be mapped on path '/ski-slopes'.
     * After the ski slope is created, all ski slopes should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/ski-slopes")
    @PreAuthorize("hasRole('ADMIN')")
    public String create(@RequestParam String name,
                         @RequestParam Integer length,
                         @RequestParam SlopeDifficulty difficulty,
                         @RequestParam Long skiResort) {
        skiSlopeService.create(name, length, difficulty, skiResort);
        return "redirect:/ski-slopes";
    }

    /**
     * This method should update a ski slope given the arguments it takes.
     * The method should be mapped on path '/ski-slopes/[id]'.
     * After the ski slope is updated, all ski slopes should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/ski-slopes/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String update(@PathVariable Long id,
                         @RequestParam String name,
                         @RequestParam Integer length,
                         @RequestParam SlopeDifficulty difficulty,
                         @RequestParam Long skiResort) {
        skiSlopeService.update(id, name, length, difficulty, skiResort);
        return "redirect:/ski-slopes";
    }

    /**
     * This method should delete the ski slope that has the appropriate identifier.
     * The method should be mapped on path '/ski-slopes/delete/[id]'.
     * After the ski slope is deleted, all ski slopes should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/ski-slopes/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
        skiSlopeService.delete(id);
        return "redirect:/ski-slopes";
    }

    /**
     * This method should close a ski slope.
     * The method should be mapped on path '/ski-slopes/close/[id]'.
     * After the selected ski slope is closed, all ski slopes should be displayed.
     *
     * @param id The ID of the ski slope to close.
     * @return The view "list.html".
     */
    @PostMapping("/ski-slopes/close/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String close(@PathVariable Long id) {
        skiSlopeService.close(id);
        return "redirect:/ski-slopes";
    }

}