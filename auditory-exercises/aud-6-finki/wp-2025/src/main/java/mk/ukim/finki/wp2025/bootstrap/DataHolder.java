package mk.ukim.finki.wp2025.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp2025.model.*;
import mk.ukim.finki.wp2025.repository.jpa.CategoryRepository;
import mk.ukim.finki.wp2025.repository.jpa.ManufacturerRepository;
import mk.ukim.finki.wp2025.repository.jpa.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {

    public static List<Category> categories = null;
    public static List<Manufacturer> manufacturers = null;
    public static List<Product> products = null;
    public static List<User> users = null;
    public static List<ShoppingCart> shoppingCarts = null;

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ManufacturerRepository manufacturerRepository;

    public DataHolder(
            UserRepository userRepository,
            CategoryRepository categoryRepository,
            ManufacturerRepository manufacturerRepository
    ) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @PostConstruct
    public void init() {
        if (categoryRepository.findAll().isEmpty()) {
            categories = new ArrayList<>();
            categories.add(new Category("Movies", "Movies Category"));
            categories.add(new Category("Books", "Books Category"));
            categories.add(new Category("Clothes", "Clothes Category"));
            categoryRepository.saveAll(categories);
        }

        if (manufacturerRepository.findAll().isEmpty()) {
            manufacturers = new ArrayList<>();
            manufacturers.add(new Manufacturer("Mango", "Spain"));
            manufacturers.add(new Manufacturer("Nike", "USA"));
            manufacturers.add(new Manufacturer("Amazon", "USA"));
            manufacturerRepository.saveAll(manufacturers);
        }

        if (userRepository.findAll().isEmpty()) {
            users = new ArrayList<>();
            users.add(new User("elena.atanasoska", "ea", "Elena", "Atanasoska"));
            users.add(new User("darko.sasanski", "ds", "Darko", "Sasanski"));
            users.add(new User("ana.todorovska", "at", "Ana", "Todorovska"));
            userRepository.saveAll(users);
        }

        products = new ArrayList<>();
        shoppingCarts = new ArrayList<>();
    }
}
