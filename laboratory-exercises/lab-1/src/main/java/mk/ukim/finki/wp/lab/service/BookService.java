package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.model.BookReservation;

import java.util.List;

public interface BookService {
    List<Book> listAll();
    List<Book> searchBooks(String text, double rating);
}
