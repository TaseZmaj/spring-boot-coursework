package mk.ukim.finki.wp.kol2025g3.repository;

import mk.ukim.finki.wp.kol2025g3.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Page<Expense> findAll(Specification<Expense> filter, Pageable page);
}
