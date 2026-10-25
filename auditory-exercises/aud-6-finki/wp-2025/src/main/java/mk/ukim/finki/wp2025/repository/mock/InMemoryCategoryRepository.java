package mk.ukim.finki.wp2025.repository.mock;

import mk.ukim.finki.wp2025.model.Category;

import java.util.List;
import java.util.Optional;

public interface InMemoryCategoryRepository {

    Optional<Category> findById(Long id);

    Category save(Category category);

    List<Category> findAll();

    Optional<Category> findByName(String name);

    List<Category> search(String text);

    void delete(String name);

    void delete(Long id);
}
