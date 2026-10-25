package mk.ukim.finki.wp.jan2025g2.model.exceptions;

public class InvalidParkLocationIdException extends RuntimeException {
    public InvalidParkLocationIdException(Long id ) {
        super("Invalid Park Location id:" + id);
    }
}
