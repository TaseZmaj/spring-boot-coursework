package mk.ukim.finki.wp2025.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.wp2025.model.enums.ProductLevel;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private ProductLevel level;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Manufacturer manufacturer;

    public Product(
            String name, Double price, Integer quantity, ProductLevel productLevel,
            Category category, Manufacturer manufacturer
    ) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.level = productLevel;
        this.category = category;
        this.manufacturer = manufacturer;
    }
}

