package mk.ukim.finki.wp.lab2.service.implementations;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.lab2.model.exceptions.BookNotFoundException;
import mk.ukim.finki.wp.lab2.model.Author;
import mk.ukim.finki.wp.lab2.model.Book;
import mk.ukim.finki.wp.lab2.repository.BookRepository;
import mk.ukim.finki.wp.lab2.service.AuthorService;
import mk.ukim.finki.wp.lab2.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;

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

    @Override
    public void delete(long id) {
        this.bookRepository.delete(id);
    }

    @Override
    public Book create(String title, String genre, double averageRating, long authorId) {
        Author author = authorService.findById(authorId);

        Book newBook = new Book(title, genre, averageRating, author);
        return bookRepository.save(newBook);
    }

    @Override
    public Book update(long id, String title, String genre, double averageRating, long authorId) {


        Book targetBook = bookRepository.findById(id).orElseThrow(()-> new BookNotFoundException(id));

        targetBook.setTitle(title);
        targetBook.setGenre(genre);
        targetBook.setAverageRating(averageRating);
        targetBook.setAuthor(authorService.findById(authorId));

        return bookRepository.save(targetBook);
    }

    @Override
    public Book findById(long id) {
        return bookRepository.findById(id).orElseThrow(()-> new BookNotFoundException(id));
    }
}
