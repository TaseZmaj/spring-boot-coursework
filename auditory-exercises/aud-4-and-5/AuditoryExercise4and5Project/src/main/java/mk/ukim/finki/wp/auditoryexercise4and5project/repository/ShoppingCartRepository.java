package mk.ukim.finki.wp.auditoryexercise4and5project.repository;

import mk.ukim.finki.wp.auditoryexercise4and5project.model.ShoppingCart;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.enums.ShoppingCartStatus;

import java.util.Optional;

public interface ShoppingCartRepository {
    Optional<ShoppingCart> findById(Long id);
    Optional<ShoppingCart> findByUsernameAndStatus(String username, ShoppingCartStatus status);
    ShoppingCart save(ShoppingCart shoppingCart);
}
