package mk.ukim.finki.wp.auditoryexercise4and5project.model.exceptions;

public class ManufacturerNotFoundException extends RuntimeException {
    public ManufacturerNotFoundException(Long id) {
        super("A manufacturer with an id: " + id + " does not exist");
    }
}
