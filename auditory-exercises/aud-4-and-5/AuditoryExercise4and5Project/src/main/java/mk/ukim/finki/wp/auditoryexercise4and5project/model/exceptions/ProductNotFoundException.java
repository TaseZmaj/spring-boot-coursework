package mk.ukim.finki.wp.auditoryexercise4and5project.model.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long productId) {
        super("A product with an id: " + productId + " was not found");
    }
}
