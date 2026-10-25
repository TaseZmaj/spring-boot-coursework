package mk.ukim.finki.wp.kol2025g3.web;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.kol2025g3.model.Expense;
import mk.ukim.finki.wp.kol2025g3.model.ExpenseCategory;
import mk.ukim.finki.wp.kol2025g3.repository.ExpenseRepository;
import mk.ukim.finki.wp.kol2025g3.service.ExpenseService;
import mk.ukim.finki.wp.kol2025g3.service.VendorService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
public class ExpensesController {

    private final ExpenseService expenseService;
    private final VendorService vendorService;
    /**
     * This method should use the "list.html" template to display all products.
     * The method should be mapped on paths '/' and '/expenses'.
     * The arguments that this method takes are optional and can be 'null'.
     * The filtered expenses that are the result of the call
     * findPage method from the ExpenseService should be displayed
     * If you want to return a paginated result, you should also pass the page number and the page size as arguments.
     *
     * @param title           The title which the expenses should contain
     * @param expenseCategory The category of the expenses
     * @param vendor          The id of the vendor that the expenses are related to
     * @param pageNum         The number of the page
     * @param pageSize        The size of the page
     * @return The view "list.html".
     */
    @GetMapping(path = {"/", "/expenses"})
    public String listAll(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) ExpenseCategory expenseCategory,
            @RequestParam(required = false) Long vendor,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            Model model) {

        Page<Expense> page = expenseService.findPage(title, expenseCategory, vendor, pageNum - 1, pageSize);
        model.addAttribute("page", page);
        model.addAttribute("title", title);
        model.addAttribute("expenseCategory", expenseCategory);
        model.addAttribute("vendor", vendor); //vendor id
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("pageSize", pageSize);

        model.addAttribute("categories", ExpenseCategory.values());
        model.addAttribute("vendors", vendorService.listAll());

        return "list";
    }

    /**
     * This method should display the "form.html" template.
     * The method should be mapped on path '/expenses/add'.
     *
     * @return The view "form.html".
     */
    @GetMapping("/expenses/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAdd(Model model) {
        model.addAttribute("categories", ExpenseCategory.values());
        model.addAttribute("vendors", vendorService.listAll());
        return "form";
    }

    /**
     * This method should display the "form.html" template.
     * However, in this case, all 'input' elements should be filled with the appropriate value for the expenses that is updated.
     * The method should be mapped on path '/expenses/edit/[id]'.
     *
     * @return The view "form.html".
     */
    @GetMapping("/expenses/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showEdit(@PathVariable Long id, Model model) {
        model.addAttribute("expense", expenseService.findById(id));
        model.addAttribute("categories", ExpenseCategory.values());
        model.addAttribute("vendors", vendorService.listAll());
        return "form";
    }

    /**
     * This method should create an expenses given the arguments it takes.
     * The method should be mapped on path '/expenses'.
     * After the expenses is created, all expenses should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/expenses")
    @PreAuthorize("hasRole('ADMIN')")
    public String create(String title, LocalDate dateCreated, Double amount, Integer daysToExpire, ExpenseCategory expenseCategory, Long vendor, Model model) {
        expenseService.create(title, dateCreated, amount, daysToExpire, expenseCategory, vendor);
        model.addAttribute("categories", ExpenseCategory.values());
        model.addAttribute("vendors", vendorService.listAll());
        return "redirect:/expenses";
    }

    /**
     * This method should update an expense given the arguments it takes.
     * The method should be mapped on path '/expenses/[id]'.
     * After the expense is updated, all expenses should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/expenses/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String update(@PathVariable Long id, String title, LocalDate dateCreated, Double amount, Integer daysToExpire, ExpenseCategory expenseCategory, Long vendor, Model model) {
        expenseService.update(id, title, dateCreated, amount, daysToExpire, expenseCategory, vendor);
        model.addAttribute("categories", ExpenseCategory.values());
        model.addAttribute("vendors", vendorService.listAll());
        return "redirect:/expenses";
    }

    /**
     * This method should delete the expense that has the appropriate identifier.
     * The method should be mapped on path '/expenses/delete/[id]'.
     * After the expenses is deleted, all expenses should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/expenses/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
        expenseService.delete(id);
        return "redirect:/expenses";
    }

    /**
     * This method should implement the logic for extending the expiration of an expense,
     * by adding one day to the daysToExpire.
     * The method should be mapped on path '/expenses/extend/[id]'.
     * After the operation, all expenses should be displayed.
     *
     * @return The view "list.html".
     */

    @PostMapping("/expenses/extend/{id}")
    @PreAuthorize("hasRole('USER')")
    public String extend(@PathVariable Long id) {
        expenseService.extendExpiration(id);
        return "redirect:/expenses";
    }
}
