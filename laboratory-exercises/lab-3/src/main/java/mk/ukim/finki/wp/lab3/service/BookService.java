package mk.ukim.finki.wp.lab3.service;

import mk.ukim.finki.wp.lab3.model.Book;

import java.util.List;

public interface BookService {
    List<Book> listAll();
    List<Book> searchBooks(String text, Double rating);
    List<Book> searchBooks(Long authorId);
    void delete(Long id);
    Book create(String title, String genre, Double averageRating, Long authorId);
    Book update(Long id, String title, String genre, Double averageRating, Long authorId);
    Book findById(Long id);

    List<Book> find(String title, Double rating, Long authorId);
}
