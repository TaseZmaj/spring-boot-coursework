package mk.ukim.finki.wp.auditoryexercise4and5project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.enums.ShoppingCartStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ShoppingCart {

    private Long id;
    private LocalDateTime dateCreated;
    private User user;
    private List<Product> products;
    private ShoppingCartStatus status;

    public ShoppingCart(){
        this.id = (long)(Math.random()*1000);
    }

    public ShoppingCart(User user){
        this.id = (long)(Math.random()*1000);
        this.dateCreated = LocalDateTime.now();
        this.user = user;
        this.products = new ArrayList<>();
        this.status = ShoppingCartStatus.CREATED;
    }
}
