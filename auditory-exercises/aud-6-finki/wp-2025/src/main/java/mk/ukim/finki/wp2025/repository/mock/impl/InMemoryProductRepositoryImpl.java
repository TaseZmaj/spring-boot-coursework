package mk.ukim.finki.wp2025.repository.mock.impl;

import mk.ukim.finki.wp2025.bootstrap.DataHolder;
import mk.ukim.finki.wp2025.model.Product;
import mk.ukim.finki.wp2025.repository.mock.InMemoryProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryProductRepositoryImpl implements InMemoryProductRepository {

    @Override
    public Optional<Product> findById(Long id) {
        return DataHolder.products.stream()
                                  .filter(product -> product.getId().equals(id))
                                  .findFirst();
    }

    @Override
    public Product save(Product product) {
        delete(product.getId());
        DataHolder.products.add(product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        return DataHolder.products;
    }

    @Override
    public List<Product> search(String text) {
        return DataHolder.products.stream().filter(p -> p.getName().toLowerCase().contains(text.toLowerCase())
                                 || p.getLevel().toString().toLowerCase().contains(text.toLowerCase())
                                 || p.getCategory().getName().toLowerCase().contains(text.toLowerCase())
                                 || p.getManufacturer().getName().toLowerCase().contains(text.toLowerCase()))
                                  .toList();
    }

    @Override
    public void delete(Long id) {
        DataHolder.products.removeIf(m -> m.getId().equals(id));
    }
}
