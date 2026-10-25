package mk.ukim.finki.wp.kol2025g3.service;

import mk.ukim.finki.wp.kol2025g3.model.Expense;
import mk.ukim.finki.wp.kol2025g3.model.ExpenseCategory;
import mk.ukim.finki.wp.kol2025g3.model.Vendor;
import mk.ukim.finki.wp.kol2025g3.model.exceptions.InvalidExpenseIdException;
import mk.ukim.finki.wp.kol2025g3.model.exceptions.InvalidVendorIdException;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {

    /**
     * @return List of all expenses in the database
     */
    List<Expense> listAll();

    /**
     * @param id The id of the expense that we want to obtain
     * @return The expense with the appropriate id
     * @throws InvalidExpenseIdException when there is no expense with the given id
     */
    Expense findById(Long id);

    /**
     * This method is used to create a new expense, and save it in the database.
     *
     * @param title
     * @param dateCreated
     * @param amount
     * @param daysToExpire
     * @param expenseCategory
     * @param vendorId
     * @return The expense that is created. The id should be generated when the expense is created.
     * @throws InvalidVendorIdException when there is no vendor with the given id
     */
    Expense create(String title, LocalDate dateCreated, Double amount, Integer daysToExpire, ExpenseCategory expenseCategory, Long vendorId);

    /**
     * This method is used to update an expense, and save it in the database.
     *
     * @param id              The id of the expense that is being updated
     * @param title
     * @param dateCreated
     * @param amount
     * @param daysToExpire
     * @param expenseCategory
     * @param vendorId
     * @return The expense that is updated.
     * @throws InvalidExpenseIdException when there is no expense with the given id
     * @throws InvalidVendorIdException  when there is no vendor with the given id
     */
    Expense update(Long id, String title, LocalDate dateCreated, Double amount, Integer daysToExpire, ExpenseCategory expenseCategory, Long vendorId);

    /**
     * @param id
     * @return The expense that is deleted.
     * @throws InvalidExpenseIdException when there is no expense with the given id
     */
    Expense delete(Long id);

    /**
     * This method should implement the logic for extending the expiration of an expense,
     * by adding one day to the daysToExpire.
     *
     * @param id
     * @return The expense, which expiration is extended.
     * @throws InvalidExpenseIdException when there is no expense with the given id
     */
    Expense extendExpiration(Long id);

    /**
     * Returns a page of expenses that match the given criteria.
     *
     * @param title
     * @param expenseCategory
     * @param vendor
     * @param pageNum
     * @param pageSize
     * @return The page of expenses that match the given criteria.
     */
    Page<Expense> findPage(String title, ExpenseCategory expenseCategory, Long vendor, int pageNum, int pageSize);
}
