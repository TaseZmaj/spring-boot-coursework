package mk.ukim.finki.wp.lab2.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {
    private long id;
    String title;
    String genre;
    double averageRating;
    private Author author;

    public Book(String title, String genre, double averageRating, Author author) {
        this.id = (long) (Math.random() * 1000);
        this.title = title;
        this.genre = genre;
        this.averageRating = averageRating;
        this.author = author;
    }
}
