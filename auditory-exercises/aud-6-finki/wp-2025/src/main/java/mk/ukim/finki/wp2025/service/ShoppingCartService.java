package mk.ukim.finki.wp2025.service;

import mk.ukim.finki.wp2025.model.Product;
import mk.ukim.finki.wp2025.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    List<Product> listAllProductsInShoppingCart(Long cartId);

    ShoppingCart getActiveShoppingCart(String username);

    ShoppingCart addProductToShoppingCart(String username, Long productId);
}

