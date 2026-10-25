package mk.ukim.finki.wp.lab2.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Author {
    private Long id;
    private String name;
    private String surname;
    private String country;
    private String biography;

    public Author(String name, String surname, String country, String biography) {
        this.id = (long) (Math.random() * 1000);
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.biography = biography;
    }
}
