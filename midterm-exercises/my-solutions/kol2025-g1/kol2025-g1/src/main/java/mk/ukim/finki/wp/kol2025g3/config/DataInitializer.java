package mk.ukim.finki.wp.kol2025g3.config;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.kol2025g3.model.ExpenseCategory;
import mk.ukim.finki.wp.kol2025g3.service.ExpenseService;
import mk.ukim.finki.wp.kol2025g3.service.VendorService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer {

    private final VendorService vendorService;
    private final ExpenseService expenseService;

    public DataInitializer(VendorService vendorService, ExpenseService expenseService) {
        this.vendorService = vendorService;
        this.expenseService = expenseService;
    }


    private ExpenseCategory randomize(int i) {
        if (i % 2 == 0) return ExpenseCategory.FOOD;
        return ExpenseCategory.DRINK;
    }

    @PostConstruct
    public void initData() {
        for (int i = 1; i < 6; i++) {
            this.vendorService.create("Vendor: " + i);
        }

        for (int i = 1; i < 11; i++) {
            this.expenseService.create("Expense: " + i, LocalDate.now().minusYears(25 + i), i * 10.0, 0, this.randomize(i), this.vendorService.listAll().get((i - 1) % 5).getId());
        }
    }
}
