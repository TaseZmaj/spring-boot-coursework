package mk.ukim.finki.wp.auditoryexercise4and5project.model.exceptions;

public class InvalidUserCredentialsException extends RuntimeException {
    public InvalidUserCredentialsException() {
        super("Invalid user credentials exception");
    }
}
