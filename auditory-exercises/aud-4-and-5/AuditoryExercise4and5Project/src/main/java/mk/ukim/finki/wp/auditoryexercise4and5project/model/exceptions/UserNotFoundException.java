package mk.ukim.finki.wp.auditoryexercise4and5project.model.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("The user with a username: " + username + " does not exist");
    }
}
