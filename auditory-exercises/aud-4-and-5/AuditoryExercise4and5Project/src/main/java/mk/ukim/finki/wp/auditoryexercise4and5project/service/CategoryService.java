package mk.ukim.finki.wp.auditoryexercise4and5project.service;

import mk.ukim.finki.wp.auditoryexercise4and5project.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
        Category create(String name, String description);

        Category update(String name, String description);

        void delete(String name);

        List<Category> listCategories();

        List<Category> searchCategories(String searchText);

        Category findById(Long id);
}

