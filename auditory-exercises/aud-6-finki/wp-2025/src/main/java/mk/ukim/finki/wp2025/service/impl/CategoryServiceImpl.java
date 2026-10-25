package mk.ukim.finki.wp2025.service.impl;

import mk.ukim.finki.wp2025.model.Category;
import mk.ukim.finki.wp2025.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wp2025.repository.jpa.CategoryRepository;
import mk.ukim.finki.wp2025.repository.mock.InMemoryCategoryRepository;
import mk.ukim.finki.wp2025.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> listCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Override
    public Category create(String name, String description) {
        if (name == null || name.isEmpty() || description == null || description.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Category category = new Category(name, description);

        return this.categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, String name, String description) {
        if (name == null || name.isEmpty() || description == null || description.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));

        category.setName(name);
        category.setDescription(description);

        return this.categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        this.categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> searchCategories(String text) {
        return categoryRepository.findAll();
    }
}
