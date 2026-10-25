package mk.ukim.finki.wp.auditoryexercise4and5project.service.implementations;

import mk.ukim.finki.wp.auditoryexercise4and5project.model.Category;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.Manufacturer;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.Product;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.enums.ProductLevel;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.exceptions.ProductNotFoundException;
import mk.ukim.finki.wp.auditoryexercise4and5project.repository.CategoryRepository;
import mk.ukim.finki.wp.auditoryexercise4and5project.repository.ManufacturerRepository;
import mk.ukim.finki.wp.auditoryexercise4and5project.repository.ProductRepository;
import mk.ukim.finki.wp.auditoryexercise4and5project.service.CategoryService;
import mk.ukim.finki.wp.auditoryexercise4and5project.service.ManufacturerService;
import mk.ukim.finki.wp.auditoryexercise4and5project.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    //PAZI GO OVA TUKA - Servisite gi dodavas pokraj product repository-to:
    private final ManufacturerService manufacturerService;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, ManufacturerService manufacturerService, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.manufacturerService = manufacturerService;
        this.categoryService = categoryService;
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new ProductNotFoundException(id));
    }

    @Override
    public Product create(String name, Double price, Integer quantity, ProductLevel level, Long categoryId, Long manufacturerId) {
        Category category = categoryService.findById(categoryId);
        Manufacturer manufacturer = manufacturerService.findById(manufacturerId);

        Product product = new Product(name, price, quantity, level, category, manufacturer);
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, String name, Double price, Integer quantity, ProductLevel level, Long categoryId, Long manufacturerId) {
        Product product = findById(id);
        Category category = categoryService.findById(categoryId);
        Manufacturer manufacturer = manufacturerService.findById(manufacturerId);

        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setLevel(level);
        product.setCategory(category);
        product.setManufacturer(manufacturer);

        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> search(String text) {
        return productRepository.search(text);
    }

    @Override
    public void delete(Long id) {
        productRepository.delete(id);
    }
}
