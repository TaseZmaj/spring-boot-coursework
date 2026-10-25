package mk.ukim.finki.wp.lab2.service;

import mk.ukim.finki.wp.lab2.model.Book;

import java.util.List;

public interface BookService {
    List<Book> listAll();
    List<Book> searchBooks(String text, double rating);
    void delete(long id);
    Book create(String title, String genre, double averageRating, long authorId);
    Book update(long id, String title, String genre, double averageRating, long authorId);
    Book findById(long id);
}
