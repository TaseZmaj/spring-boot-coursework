package mk.ukim.finki.wp.lab4.service.implementations;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.lab4.model.exceptions.AuthorNotFoundException;
import mk.ukim.finki.wp.lab4.model.exceptions.BookNotFoundException;
import mk.ukim.finki.wp.lab4.model.Author;
import mk.ukim.finki.wp.lab4.model.Book;
import mk.ukim.finki.wp.lab4.repository.jpa.BookRepository;
import mk.ukim.finki.wp.lab4.service.AuthorService;
import mk.ukim.finki.wp.lab4.service.BookService;
import org.springframework.data.jpa.domain.Specification;
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
    public List<Book> searchBooks(String text, Double rating) {
        if(text == null) {
            throw new IllegalArgumentException("Book title must not be null");
        }
        if(rating < 0 || rating > 5){
            throw new IllegalArgumentException("Rating is smaller than 0 or larger than 5");
        }
        if(text.isEmpty()) {
            return bookRepository.findAll();
        }
        return bookRepository.findByTitleContainingIgnoreCaseAndAverageRatingGreaterThanEqual(text, rating);
    }

    @Override
    public List<Book> searchBooks(Long authorId) {
        if(authorId == null) {
            throw new IllegalArgumentException("Author id must not be null");
        }
        return bookRepository.findByAuthor_Id(authorId);
    }

    @Override
    public List<Book> find(String title, Double averageRating, Long authorId) {
        Specification<Book> specification = Specification.allOf(
                FieldFilterSpecification.filterContainsText(Book.class, "title", title),
                FieldFilterSpecification.greaterThan(Book.class, "averageRating", averageRating),
                FieldFilterSpecification.filterEquals(Book.class, "author.id", authorId)
        );

        return this.bookRepository.findAll(specification);
    }

    @Override
    public void delete(Long id) {
        this.bookRepository.deleteById(id);
    }

    @Override
    public Book create(String title, String genre, Double averageRating, Long authorId) {
        Author author = authorService.findById(authorId).orElseThrow(()-> new AuthorNotFoundException(authorId));

        Book newBook = new Book(title, genre, averageRating, author);
        return bookRepository.save(newBook);
    }

    @Override
    public Book update(Long id, String title, String genre, Double averageRating, Long authorId) {


        Book targetBook = bookRepository.findById(id).orElseThrow(()-> new BookNotFoundException(id));

        targetBook.setTitle(title);
        targetBook.setGenre(genre);
        targetBook.setAverageRating(averageRating);
        targetBook.setAuthor(authorService.findById(authorId).orElseThrow(()-> new AuthorNotFoundException(authorId)));

        return bookRepository.save(targetBook);
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(()-> new BookNotFoundException(id));
    }

}
