package mk.ukim.finki.wp.kol2025g2.model.exceptions;

public class InvalidSkiSlopeIdException extends RuntimeException {
    public InvalidSkiSlopeIdException(Long id){
        super("There is no Ski Slope with an id: " + id);
    }
}
