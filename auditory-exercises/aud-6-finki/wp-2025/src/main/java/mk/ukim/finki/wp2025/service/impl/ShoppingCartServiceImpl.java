package mk.ukim.finki.wp2025.service.impl;

import mk.ukim.finki.wp2025.model.Product;
import mk.ukim.finki.wp2025.model.ShoppingCart;
import mk.ukim.finki.wp2025.model.User;
import mk.ukim.finki.wp2025.model.enums.ShoppingCartStatus;
import mk.ukim.finki.wp2025.model.exceptions.ProductAlreadyInShoppingCartException;
import mk.ukim.finki.wp2025.model.exceptions.ShoppingCartNotFoundException;
import mk.ukim.finki.wp2025.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wp2025.repository.jpa.ShoppingCartRepository;
import mk.ukim.finki.wp2025.repository.jpa.UserRepository;
import mk.ukim.finki.wp2025.repository.mock.InMemoryShoppingCartRepository;
import mk.ukim.finki.wp2025.repository.mock.InMemoryUserRepository;
import mk.ukim.finki.wp2025.service.ProductService;
import mk.ukim.finki.wp2025.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ProductService productService;

    public ShoppingCartServiceImpl(
            ShoppingCartRepository shoppingCartRepository,
            UserRepository userRepository,
            ProductService productService
    ) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.productService = productService;
    }

    @Override
    public List<Product> listAllProductsInShoppingCart(Long cartId) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                                                  .orElseThrow(() -> new ShoppingCartNotFoundException(cartId));
        return cart.getProducts();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        return this.shoppingCartRepository
                .findByUserAndStatus(user, ShoppingCartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart shoppingCart = new ShoppingCart(user);
                    return this.shoppingCartRepository.save(shoppingCart);
                });
    }

    @Override
    public ShoppingCart addProductToShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Product product = this.productService.findById(productId);
        if (shoppingCart.getProducts()
                        .stream().anyMatch(i -> i.getId().equals(productId)))
            throw new ProductAlreadyInShoppingCartException(productId, username);
        shoppingCart.getProducts().add(product);
        return this.shoppingCartRepository.save(shoppingCart);
    }
}

