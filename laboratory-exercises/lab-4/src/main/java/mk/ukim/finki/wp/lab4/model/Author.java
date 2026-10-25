package mk.ukim.finki.wp.lab4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String surname;
    private String country;
    private String biography;

    @OneToMany(mappedBy="author", fetch=FetchType.EAGER)
    private List<Book> books;

    public Author(String name, String surname, String country, String biography) {
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.biography = biography;
    }
}
