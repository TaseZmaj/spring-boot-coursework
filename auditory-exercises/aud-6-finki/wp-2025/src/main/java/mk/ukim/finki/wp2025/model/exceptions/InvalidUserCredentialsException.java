package mk.ukim.finki.wp2025.model.exceptions;

public class InvalidUserCredentialsException extends RuntimeException {
    public InvalidUserCredentialsException() {
        super("Invalid user credentials exception");
    }
}