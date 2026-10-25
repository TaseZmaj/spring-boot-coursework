package mk.ukim.finki.wp.lab2.repository;

import mk.ukim.finki.wp.lab2.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();
    List<Book> searchBooks(String text, double rating);
    Optional<Book> findById(long id);
    void delete(long id);
    Book save(Book book);
}
