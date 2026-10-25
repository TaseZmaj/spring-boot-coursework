package mk.ukim.finki.wp.lab2.repository;

import mk.ukim.finki.wp.lab2.model.Author;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository {
    List<Author> findAll();
    Author findById(long id);
}
