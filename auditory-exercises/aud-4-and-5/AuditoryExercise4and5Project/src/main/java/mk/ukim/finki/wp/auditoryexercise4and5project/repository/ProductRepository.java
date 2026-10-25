package mk.ukim.finki.wp.auditoryexercise4and5project.repository;

import mk.ukim.finki.wp.auditoryexercise4and5project.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(long id);
    Product save(Product product);
    List<Product> findAll();
    List<Product> search(String text);
    void delete(Long id);
}
