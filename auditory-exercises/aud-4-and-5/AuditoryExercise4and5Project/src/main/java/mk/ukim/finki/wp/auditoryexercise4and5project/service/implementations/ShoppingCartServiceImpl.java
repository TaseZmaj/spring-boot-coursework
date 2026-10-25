package mk.ukim.finki.wp.auditoryexercise4and5project.service.implementations;

import mk.ukim.finki.wp.auditoryexercise4and5project.model.Product;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.ShoppingCart;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.User;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.enums.ShoppingCartStatus;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.exceptions.ProductAlreadyInShoppingCartException;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.exceptions.ShoppingCartNotFoundException;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wp.auditoryexercise4and5project.repository.ShoppingCartRepository;
import mk.ukim.finki.wp.auditoryexercise4and5project.repository.UserRepository;
import mk.ukim.finki.wp.auditoryexercise4and5project.service.ProductService;
import mk.ukim.finki.wp.auditoryexercise4and5project.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
//PAZI tuka e UserRepository, NE AuthService bidejki AuthService metodite ne se pogodni za ova
    private final UserRepository userRepository;

    private final ProductService productService;

    public ShoppingCartServiceImpl(
            ShoppingCartRepository shoppingCartRepository,
            ProductService productService,
            UserRepository userRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
        this.userRepository = userRepository;
    }

    @Override
    public List<Product> listAllProductInShoppingCart(Long cartId) {
        if(this.shoppingCartRepository.findById(cartId).isEmpty()) {
            throw new ShoppingCartNotFoundException(cartId);
        }
        //NOTE: Moras .get() da dodades - vaka se pravi bidejki e Optional<ShoppingCart>
        return this.shoppingCartRepository.findById(cartId).get().getProducts();
    }

    //Ideata tuka e - za momentalno logiraniot user -> najdi ja koshnickata, a ako ja nema, kreiraj nova
    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        return this.shoppingCartRepository
                .findByUsernameAndStatus(username, ShoppingCartStatus.CREATED)
                .orElseGet(()->{ //Ako ne najdes Shopping card za user-ot, kreiraj nova:
                    User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

                    ShoppingCart shoppingCart = new ShoppingCart(user);
                    return this.shoppingCartRepository.save(shoppingCart);
                });
    }

    @Override
    public ShoppingCart addProductToShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Product product = productService.findById(productId);

        //Ako go najdes Produktot vo shopping cartot - javi error
        if(shoppingCart.getProducts().stream().anyMatch(sc -> sc.getId().equals(product.getId()))) {
            throw new ProductAlreadyInShoppingCartException(productId, username);
        }
        shoppingCart.getProducts().add(product);
        return this.shoppingCartRepository.save(shoppingCart);
    }
}
