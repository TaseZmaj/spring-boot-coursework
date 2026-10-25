package mk.ukim.finki.wp.kol2025g3.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.kol2025g3.model.Expense;
import mk.ukim.finki.wp.kol2025g3.model.ExpenseCategory;
import mk.ukim.finki.wp.kol2025g3.model.Vendor;
import mk.ukim.finki.wp.kol2025g3.model.exceptions.InvalidExpenseIdException;
import mk.ukim.finki.wp.kol2025g3.repository.ExpenseRepository;
import mk.ukim.finki.wp.kol2025g3.service.ExpenseService;
import mk.ukim.finki.wp.kol2025g3.service.VendorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static mk.ukim.finki.wp.kol2025g3.service.specifications.FieldFilterSpecification.*;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final VendorService vendorService;

    @Override
    public List<Expense> listAll() {
        return expenseRepository.findAll();
    }

    @Override
    public Expense findById(Long id) {
        return expenseRepository.findById(id).orElseThrow(() -> new InvalidExpenseIdException(id));
    }

    @Override
    public Expense create(
            String title,
            LocalDate dateCreated,
            Double amount,
            Integer daysToExpire,
            ExpenseCategory expenseCategory,
            Long vendorId) {
        if (title == null || title.isEmpty() ||
                dateCreated == null ||
                amount == null ||
                daysToExpire == null ||
                expenseCategory == null ||
                vendorId == null) {
            throw new IllegalArgumentException();
        }
        Vendor vendor = vendorService.findById(vendorId);
        Expense expense = new Expense(title, dateCreated, amount, daysToExpire, expenseCategory, vendor);

        return expenseRepository.save(expense);
    }

    @Override
    public Expense update(Long id, String title, LocalDate dateCreated, Double amount, Integer daysToExpire, ExpenseCategory expenseCategory, Long vendorId) {
        {
            if (title == null || title.isEmpty() ||
                    dateCreated == null ||
                    amount == null ||
                    daysToExpire == null ||
                    expenseCategory == null ||
                    vendorId == null) {
                throw new IllegalArgumentException();
            }

            Expense expense = this.findById(id);
            Vendor vendor = vendorService.findById(vendorId);

            expense.setTitle(title);
            expense.setDateCreated(dateCreated);
            expense.setAmount(amount);
            expense.setDaysToExpire(daysToExpire);
            expense.setExpenseCategory(expenseCategory);
            expense.setVendor(vendor);

            return expenseRepository.save(expense);
        }

    }

    @Override
    public Expense delete(Long id) {
        Expense expense = this.findById(id);
        expenseRepository.deleteById(id);

        return expense;
    }

    @Override
    public Expense extendExpiration(Long id) {
        Expense expense = this.findById(id);
        expense.setDaysToExpire(expense.getDaysToExpire() + 1);

        return expenseRepository.save(expense);
    }

    @Override
    public Page<Expense> findPage(String title, ExpenseCategory expenseCategory, Long vendor, int pageNum, int pageSize) {
        Specification<Expense> specification = Specification.allOf(
                filterContainsText(Expense.class, "title", title),
                filterEqualsV(Expense.class, "expenseCategory", expenseCategory),
                filterEquals(Expense.class, "vendor.id", vendor)
        );

        return expenseRepository.findAll(specification, PageRequest.of(pageNum, pageSize));
    }
}





