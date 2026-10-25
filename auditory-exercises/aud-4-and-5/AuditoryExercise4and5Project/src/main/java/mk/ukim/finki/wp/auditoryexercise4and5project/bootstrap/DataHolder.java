package mk.ukim.finki.wp.auditoryexercise4and5project.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.*;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.enums.ProductLevel;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.enums.ShoppingCartStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component //znachi deka ke se instancira samata klasa koga ke startuva aplikacijata
public class DataHolder {
    public static List<Category> categories = null;
    public static List<User> users = null;
    public static List<Manufacturer> manufacturers = null;
    public static List<Product> products = null;
    public static List<ShoppingCart> shoppingCarts = null;

    //init()
    // --> nema sam po sebe da se povika zatoa:
    @PostConstruct
    public void init(){
        categories = new ArrayList<>();
        categories.add(new Category("Software", "(Software Description)"));
        categories.add(new Category("Movies", "(Movies Description)"));
        categories.add(new Category("Books", "(Books Description)"));
        categories.add(new Category("Hardware", "(Hardware Description)"));

        users = new ArrayList<>();
        users.add(new User("elena.atanasoska", "ea", "Elena", "Atanasoska"));
        users.add(new User("darko.sasanski", "ds", "Darko", "Sasanski"));
        users.add(new User("ana.todorovska", "at", "Ana", "Todorovska"));

        manufacturers = new ArrayList<>();
        manufacturers.add(new Manufacturer("Apple", "One Apple Park Way, Cupertino, CA 95014"));

        products = new ArrayList<>();
        products.add(new Product("IPhone 15", 999.0, 30, ProductLevel.NORMAL, categories.get(3), manufacturers.getFirst()));

        shoppingCarts = new ArrayList<>();
    }
}
