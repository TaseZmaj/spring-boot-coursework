package mk.ukim.finki.wp.lab.service.implementations;

import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.repository.BookRepository;
import mk.ukim.finki.wp.lab.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> listAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public List<Book> searchBooks(String text, double rating) {
        if(text == null) {
            throw new IllegalArgumentException("Book title must not be null");
        }
        if(rating < 0 || rating > 5){
            throw new IllegalArgumentException("Rating is smaller than 0 or larger than 5");
        }
        if(text.isEmpty()) {
            return bookRepository.findAll();
        }
        return bookRepository.searchBooks(text, rating);
    }
}
