package mk.ukim.finki.wp.auditoryexercise4and5project.model.exceptions;

public class PasswordsDoNotMatchException extends RuntimeException {
    public PasswordsDoNotMatchException() {
        super("Passwords do not match exception");
    }
}
