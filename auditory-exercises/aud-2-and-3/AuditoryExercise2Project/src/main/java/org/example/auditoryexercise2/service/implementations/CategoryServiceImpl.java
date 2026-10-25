package org.example.auditoryexercise2.service.implementations;

import org.example.auditoryexercise2.model.Category;
import org.example.auditoryexercise2.repository.implementations.InMemoryCategoryRepository;
import org.example.auditoryexercise2.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final InMemoryCategoryRepository categoryRepository;

    public CategoryServiceImpl(InMemoryCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(String name, String description) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("name is null or empty");
        }
        Category c = new Category(name, description);
        categoryRepository.save(c);
        return c;
    }

    @Override
    public Category update(String name, String description) {
        //isti se so create() funkcijata gore bidejki save() e napishana na nacin
        //taka shto moze da se overwritne ako vekje postoi nekoja kategorija
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("name is null or empty");
        }
        Category c = new Category(name, description);
        categoryRepository.save(c);
        return c;
    }

    @Override
    public void delete(String name) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("name is null or empty");
        }
        categoryRepository.delete(name);
    }

    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> searchCategories(String searchText) {
        return categoryRepository.search(searchText);
    }
}
