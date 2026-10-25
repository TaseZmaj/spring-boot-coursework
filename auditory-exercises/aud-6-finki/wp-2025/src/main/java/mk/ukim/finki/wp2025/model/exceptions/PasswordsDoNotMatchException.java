package mk.ukim.finki.wp2025.model.exceptions;

public class PasswordsDoNotMatchException extends RuntimeException {
    public PasswordsDoNotMatchException() {
        super("Passwords do not match exception");
    }
}