package mk.ukim.finki.wp.lab2.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab2.model.Author;
import mk.ukim.finki.wp.lab2.model.Book;
import mk.ukim.finki.wp.lab2.model.BookReservation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Book> books = new ArrayList<>();
    public static List<BookReservation> reservations = new ArrayList<>();
    public static List<Author> authors = new ArrayList<>();

    @PostConstruct
    public void init(){
        authors.add(new Author(
                1L,
                "Joanne Kathleen",
                "Rowling",
                "United Kingdom",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
        authors.add(new Author(
                2L,
                "George",
                "Orwell",
                "United Kingdom",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        ));
        authors.add(new Author(
                3L,
                "Haruki",
                "Murakami",
                "Japan",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        ));


        books.add(new Book("To Kill a Mockingbird", "Fiction/Southern Gothic", 4.61, authors.get(0)));
        books.add(new Book("1984", "Dystopian Fiction", 4.54, authors.get(1)));
        books.add(new Book("The Great Gatsby", "Classic Fiction", 4.38, authors.get(2)));
        books.add(new Book("Pride and Prejudice", "Romance/Classic", 4.59, authors.get(0)));
        books.add(new Book("The Catcher in the Rye", "Fiction/Coming-of-Age", 4.20, authors.get(1)));
        books.add(new Book("The Hobbit", "Fantasy/Adventure", 4.77, authors.get(2)));
        books.add(new Book("Moby-Dick", "Adventure/Classic", 4.15, authors.get(0)));
        books.add(new Book("Brave New World", "Science Fiction/Dystopian", 4.46, authors.get(1)));
        books.add(new Book("The Lord of the Rings", "Fantasy/Epic", 4.90, authors.get(2)));
        books.add(new Book("Harry Potter and the Sorcerer’s Stone", "Fantasy/Young Adult", 4.82, authors.get(0)));

        reservations.add(new BookReservation("To Kill a Mockingbird", "Marko", "12345 St. Skopje", 2L));
    }
}
