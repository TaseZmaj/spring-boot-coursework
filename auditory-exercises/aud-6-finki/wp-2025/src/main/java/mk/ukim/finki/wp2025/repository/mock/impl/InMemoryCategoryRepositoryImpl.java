package mk.ukim.finki.wp2025.repository.mock.impl;

import mk.ukim.finki.wp2025.bootstrap.DataHolder;
import mk.ukim.finki.wp2025.model.Category;
import mk.ukim.finki.wp2025.repository.mock.InMemoryCategoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryCategoryRepositoryImpl implements InMemoryCategoryRepository {

    @Override
    public Optional<Category> findById(Long id) {
        return DataHolder.categories.stream()
                                    .filter(c -> c.getId().equals(id))
                                    .findFirst();
    }

    @Override
    public Category save(Category category) {
        delete(category.getId());
        DataHolder.categories.add(category);
        return category;
    }

    @Override
    public List<Category> findAll() {
        return DataHolder.categories;
    }

    @Override
    public Optional<Category> findByName(String name) {
        return DataHolder.categories.stream()
                                    .filter(c -> c.getName().equals(name))
                                    .findFirst();
    }

    @Override
    public List<Category> search(String text) {
        return DataHolder.categories.stream()
                                    .filter(c -> c.getName().contains(text) ||
                                            c.getDescription().contains(text))
                                    .toList();
    }

    @Override
    public void delete(String name) {
        DataHolder.categories.removeIf(c -> c.getName().equals(name));
    }

    @Override
    public void delete(Long id) {
        DataHolder.categories.removeIf(c -> c.getId().equals(id));
    }
}

