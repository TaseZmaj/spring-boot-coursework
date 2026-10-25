package mk.ukim.finki.wp.jan2025g1.model.exceptions;

public class InvalidSiteLocationIdException extends RuntimeException {
    public InvalidSiteLocationIdException(Long id) {
        super("The Site Location with id: " + id + " doesn't exist.");
    }
}
