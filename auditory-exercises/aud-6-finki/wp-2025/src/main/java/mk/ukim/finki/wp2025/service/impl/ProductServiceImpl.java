package mk.ukim.finki.wp2025.service.impl;

import mk.ukim.finki.wp2025.model.Category;
import mk.ukim.finki.wp2025.model.Manufacturer;
import mk.ukim.finki.wp2025.model.Product;
import mk.ukim.finki.wp2025.model.enums.ProductLevel;
import mk.ukim.finki.wp2025.model.exceptions.ProductNotFoundException;
import mk.ukim.finki.wp2025.repository.jpa.ProductRepository;
import mk.ukim.finki.wp2025.repository.mock.InMemoryProductRepository;
import mk.ukim.finki.wp2025.service.CategoryService;
import mk.ukim.finki.wp2025.service.ManufacturerService;
import mk.ukim.finki.wp2025.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ManufacturerService manufacturerService;
    private final CategoryService categoryService;

    public ProductServiceImpl(
            ProductRepository productRepository,
            ManufacturerService manufacturerService,
            CategoryService categoryService
    ) {
        this.productRepository = productRepository;
        this.manufacturerService = manufacturerService;
        this.categoryService = categoryService;
    }

    @Override
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product create(
            String name,
            Double price,
            Integer quantity,
            ProductLevel level,
            Long categoryId,
            Long manufacturerId
    ) {
        if (name == null || name.isEmpty() ||
                price == null || price <= 0 ||
                quantity == null || quantity < 0 ||
                level == null ||
                categoryId == null ||
                manufacturerId == null) {
            throw new IllegalArgumentException();
        }

        Category category = categoryService
                .findById(categoryId);

        Manufacturer manufacturer = manufacturerService
                .findById(manufacturerId);

        Product product = new Product(name, price, quantity, level, category, manufacturer);
        return productRepository.save(product);
    }

    @Override
    public Product update(
            Long id,
            String name,
            Double price,
            Integer quantity,
            ProductLevel level,
            Long categoryId,
            Long manufacturerId
    ) {
        if (name == null || name.isEmpty() ||
                price == null || price <= 0 ||
                quantity == null || quantity < 0 ||
                level == null ||
                categoryId == null ||
                manufacturerId == null) {
            throw new IllegalArgumentException();
        }

        Product product = findById(id);

        Category category = categoryService
                .findById(categoryId);

        Manufacturer manufacturer = manufacturerService
                .findById(manufacturerId);

        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setLevel(level);
        product.setCategory(category);
        product.setManufacturer(manufacturer);
        return productRepository.save(product);
    }

    @Override
    public List<Product> search(String text) {
        return productRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
