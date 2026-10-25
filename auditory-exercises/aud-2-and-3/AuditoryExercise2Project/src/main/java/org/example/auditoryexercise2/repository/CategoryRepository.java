package org.example.auditoryexercise2.repository;

import org.example.auditoryexercise2.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> findAll();
    Category save(Category c);
    Optional<Category> findByName(String name);
    List<Category> search(String text);
    void delete(String name);
}
