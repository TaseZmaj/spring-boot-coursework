package mk.ukim.finki.wp.jan2025g1.model.exceptions;

public class InvalidArchaeologicalSiteIdException extends RuntimeException {
    public InvalidArchaeologicalSiteIdException(Long id) {
        super("The Archeological Site with id: " + id + " doesn't exist.");
    }
}