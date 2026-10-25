package mk.ukim.finki.wp.lab4.model.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) {
        super("Book with id=" + id + " does not exist.");
    }
}
