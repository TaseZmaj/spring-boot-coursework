package mk.ukim.finki.wp.kol2025g3.web;

import mk.ukim.finki.wp.kol2025g3.model.Expense;
import mk.ukim.finki.wp.kol2025g3.model.ExpenseCategory;
import mk.ukim.finki.wp.kol2025g3.service.ExpenseService;
import mk.ukim.finki.wp.kol2025g3.service.VendorService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping(path = {"/", "/expenses"})
public class ExpensesController {

    private final ExpenseService expenseService;
    private final VendorService vendorService;

    public ExpensesController(ExpenseService expenseService, VendorService vendorService) {
        this.expenseService = expenseService;
        this.vendorService = vendorService;
    }

    /**
     * This method should use the "list.html" template to display all products.
     * The method should be mapped on paths '/' and '/expenses'.
     * The arguments that this method takes are optional and can be 'null'.
     * The filtered expenses that are the result of the call
     * findPage method from the ExpenseService should be displayed
     * If you want to return a paginated result, you should also pass the page number and the page size as arguments.
     *
     * @param title
     * @param expenseCategory
     * @param vendor
     * @param pageNum
     * @param pageSize
     * @return The view "list.html".
     */
    @GetMapping
    public String listAll(@RequestParam(required = false) String title, @RequestParam(required = false) ExpenseCategory expenseCategory, @RequestParam(required = false) Long vendor, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, Model model) {
        Page<Expense> page = expenseService.findPage(title, expenseCategory, vendor, pageNum - 1, pageSize);
        model.addAttribute("page", page);
        model.addAttribute("title", title);
        model.addAttribute("expenseCategory", expenseCategory);
        model.addAttribute("vendor", vendor);
        model.addAttribute("categories", ExpenseCategory.values());
        model.addAttribute("vendors", vendorService.listAll());
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("pageSize", pageSize);
        return "list";
    }

    /**
     * This method should display the "form.html" template.
     * The method should be mapped on path '/expenses/add'.
     *
     * @return The view "form.html".
     */
    @GetMapping("/add")
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
    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable Long id, Model model) {
        model.addAttribute("categories", ExpenseCategory.values());
        model.addAttribute("vendors", vendorService.listAll());
        model.addAttribute("expense", expenseService.findById(id));
        return "form";
    }

    /**
     * This method should create a expenses given the arguments it takes.
     * The method should be mapped on path '/expenses'.
     * After the expenses is created, all expenses should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping
    public String create(@RequestParam String title, @RequestParam LocalDate dateCreated, @RequestParam Double amount, @RequestParam Integer daysToExpire, @RequestParam ExpenseCategory expenseCategory, @RequestParam Long vendor) {
        this.expenseService.create(title, dateCreated, amount, daysToExpire, expenseCategory, vendor);
        return "redirect:/expenses";
    }

    /**
     * This method should update an expense given the arguments it takes.
     * The method should be mapped on path '/expenses/[id]'.
     * After the expense is updated, all expenses should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @RequestParam String title, @RequestParam LocalDate dateCreated, @RequestParam Double amount, @RequestParam Integer daysToExpire, @RequestParam ExpenseCategory expenseCategory, @RequestParam Long vendor) {
        this.expenseService.update(id, title, dateCreated, amount, daysToExpire, expenseCategory, vendor);
        return "redirect:/expenses";
    }

    /**
     * This method should delete the expense that has the appropriate identifier.
     * The method should be mapped on path '/expenses/delete/[id]'.
     * After the expenses is deleted, all expenses should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        this.expenseService.delete(id);
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
    @PostMapping("/extend/{id}")
    public String extend(@PathVariable Long id) {
        this.expenseService.extendExpiration(id);
        return "redirect:/expenses";
    }
}
