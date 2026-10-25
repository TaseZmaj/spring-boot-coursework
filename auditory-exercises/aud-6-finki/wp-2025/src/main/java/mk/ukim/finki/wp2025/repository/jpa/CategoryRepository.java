package mk.ukim.finki.wp2025.repository.jpa;

import mk.ukim.finki.wp2025.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> { }
