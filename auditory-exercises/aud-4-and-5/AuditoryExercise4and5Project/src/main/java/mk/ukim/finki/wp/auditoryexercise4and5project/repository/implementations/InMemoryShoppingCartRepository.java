package mk.ukim.finki.wp.auditoryexercise4and5project.repository.implementations;

import mk.ukim.finki.wp.auditoryexercise4and5project.bootstrap.DataHolder;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.ShoppingCart;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.enums.ShoppingCartStatus;
import mk.ukim.finki.wp.auditoryexercise4and5project.repository.ShoppingCartRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InMemoryShoppingCartRepository implements ShoppingCartRepository {
    @Override
    public Optional<ShoppingCart> findById(Long id) {
        return DataHolder.shoppingCarts.stream().filter(sc -> sc.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<ShoppingCart> findByUsernameAndStatus(String username, ShoppingCartStatus status) {
        return DataHolder.shoppingCarts.stream()
                .filter(
                        sc -> sc.getUser().getUsername().equals(username)
                        && sc.getStatus().equals(status))
                .findFirst();

    }

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {
        DataHolder.shoppingCarts.removeIf(sc -> sc.getUser().getUsername().equals(shoppingCart.getUser().getUsername()));
        DataHolder.shoppingCarts.add(shoppingCart);
        return shoppingCart;
    }
}
