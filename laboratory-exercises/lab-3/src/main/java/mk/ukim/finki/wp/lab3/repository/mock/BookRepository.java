package mk.ukim.finki.wp.lab3.repository.mock;

import mk.ukim.finki.wp.lab3.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();
    List<Book> searchBooks(String text, double rating);
    Optional<Book> findById(long id);
    void delete(long id);
    Book save(Book book);
}
