package mk.ukim.finki.wp.auditoryexercise4and5project.model.exceptions;

public class ShoppingCartNotFoundException extends RuntimeException {
    public ShoppingCartNotFoundException(Long id) {
        super("A ShoppingCart with an id: " + id + "does not exist");
    }
}
