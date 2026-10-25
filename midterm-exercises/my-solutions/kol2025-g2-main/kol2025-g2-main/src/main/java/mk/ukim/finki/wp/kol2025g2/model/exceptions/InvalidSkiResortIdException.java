package mk.ukim.finki.wp.kol2025g2.model.exceptions;

public class InvalidSkiResortIdException extends RuntimeException {
    public InvalidSkiResortIdException(Long id) {
        super("There is no Ski Resort wint an id: " + id);
    }
}
