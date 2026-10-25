package mk.ukim.finki.wp.auditoryexercise4and5project.service;

import mk.ukim.finki.wp.auditoryexercise4and5project.model.Product;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    List<Product> listAllProductInShoppingCart(Long cartId);

    ShoppingCart getActiveShoppingCart(String username);

    ShoppingCart addProductToShoppingCart(String username, Long productId);

}
