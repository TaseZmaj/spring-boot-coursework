package mk.ukim.finki.wp2025.repository.jpa;

import mk.ukim.finki.wp2025.model.ShoppingCart;
import mk.ukim.finki.wp2025.model.User;
import mk.ukim.finki.wp2025.model.enums.ShoppingCartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByUserAndStatus(User user, ShoppingCartStatus shoppingCartStatus);
}
