package mk.ukim.finki.wp2025.service;

import mk.ukim.finki.wp2025.model.Product;
import mk.ukim.finki.wp2025.model.enums.ProductLevel;

import java.util.List;

public interface ProductService {
    List<Product> listProducts();

    Product findById(Long id);

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

    List<Product> search(String text);

    void delete(Long id);
}
