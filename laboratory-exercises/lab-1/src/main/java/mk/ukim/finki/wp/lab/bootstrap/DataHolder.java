package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.model.BookReservation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Book> books = new ArrayList<>();
    public static List<BookReservation> reservations = new ArrayList<>();

    @PostConstruct
    public void init(){
        books.add(new Book("To Kill a Mockingbird", "Fiction/Southern Gothic", 4.61));
        books.add(new Book("1984", "Dystopian Fiction", 4.54));
        books.add(new Book("The Great Gatsby", "Classic Fiction", 4.38));
        books.add(new Book("Pride and Prejudice", "Romance/Classic", 4.59));
        books.add(new Book("The Catcher in the Rye", "Fiction/Coming-of-Age", 4.20));
        books.add(new Book("The Hobbit", "Fantasy/Adventure", 4.77));
        books.add(new Book("Moby-Dick", "Adventure/Classic", 4.15));
        books.add(new Book("Brave New World", "Science Fiction/Dystopian", 4.46));
        books.add(new Book("The Lord of the Rings", "Fantasy/Epic", 4.90));
        books.add(new Book("Harry Potter and the Sorcerer’s Stone", "Fantasy/Young Adult", 4.82));

        reservations.add(new BookReservation("To Kill a Mockingbird", "Marko", "12345 St. Skopje", 2L));
    }
}
