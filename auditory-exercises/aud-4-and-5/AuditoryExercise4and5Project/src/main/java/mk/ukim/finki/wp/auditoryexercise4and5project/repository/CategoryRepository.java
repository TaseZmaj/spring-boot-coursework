package mk.ukim.finki.wp.auditoryexercise4and5project.repository;

import mk.ukim.finki.wp.auditoryexercise4and5project.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> findAll();
    Category save(Category c);
    Optional<Category> findByName(String name);
    Optional<Category> findById(Long id);
    List<Category> search(String text);
    void delete(String name);
}
