package mk.ukim.finki.wp.auditoryexercise4and5project.service;

import mk.ukim.finki.wp.auditoryexercise4and5project.model.Product;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.enums.ProductLevel;

import java.util.List;

public interface ProductService {
    Product create(
            String name,
            Double price,
            Integer quantity,
            ProductLevel level,
            Long categoryId,
            Long manufacturerId
    );

    Product update(
            Long id,
            String name,
            Double price,
            Integer quantity,
            ProductLevel level,
            Long categoryId,
            Long manufacturerId
    );

    List<Product> findAll();

    List<Product> search(String text);

    Product findById(Long id);

    void delete(Long id);
}
