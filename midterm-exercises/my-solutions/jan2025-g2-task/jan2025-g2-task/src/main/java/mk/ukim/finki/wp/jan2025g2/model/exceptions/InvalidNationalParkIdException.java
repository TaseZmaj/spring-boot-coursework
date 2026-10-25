package mk.ukim.finki.wp.jan2025g2.model.exceptions;

public class InvalidNationalParkIdException extends RuntimeException {
    public InvalidNationalParkIdException(Long id ) {
        super("Invalid National Park id:" + id);
    }
}