package mk.ukim.finki.wp.auditoryexercise4and5project.model.exceptions;

public class ProductAlreadyInShoppingCartException extends RuntimeException {
    public ProductAlreadyInShoppingCartException(Long productId, String username) {
        super("The product with id: " + productId + " is already in " + username + "'s shopping cart");

    }
}
