package mk.ukim.finki.wp2025.repository.mock;

import mk.ukim.finki.wp2025.model.ShoppingCart;
import mk.ukim.finki.wp2025.model.enums.ShoppingCartStatus;

import java.util.Optional;

public interface InMemoryShoppingCartRepository {

    Optional<ShoppingCart> findById(Long id);

    Optional<ShoppingCart> findByUsernameAndStatus(String username, ShoppingCartStatus status);

    ShoppingCart save(ShoppingCart shoppingCart);
}
