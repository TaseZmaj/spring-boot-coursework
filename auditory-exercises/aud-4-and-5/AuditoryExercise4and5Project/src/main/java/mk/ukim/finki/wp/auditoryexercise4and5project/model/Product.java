package mk.ukim.finki.wp.auditoryexercise4and5project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.enums.ProductLevel;

@Data
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    private ProductLevel level;
    private Category category;
    private Manufacturer manufacturer;

    public Product(
            String name,
            Double price,
            Integer quantity,
            ProductLevel level,
            Category category,
            Manufacturer manufacturer
    ) {
        this.id = (long)(Math.random()*1000);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.manufacturer = manufacturer;
        this.level = level;
    }


}
