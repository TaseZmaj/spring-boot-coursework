package mk.ukim.finki.wp.auditoryexercise4and5project.service.implementations;

import mk.ukim.finki.wp.auditoryexercise4and5project.model.Category;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wp.auditoryexercise4and5project.repository.implementations.InMemoryCategoryRepository;
import mk.ukim.finki.wp.auditoryexercise4and5project.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final InMemoryCategoryRepository categoryRepository;

    public CategoryServiceImpl(InMemoryCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
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
