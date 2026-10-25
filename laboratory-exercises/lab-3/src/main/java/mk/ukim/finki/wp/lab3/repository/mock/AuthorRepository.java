package mk.ukim.finki.wp.lab3.repository.mock;

import mk.ukim.finki.wp.lab3.model.Author;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository {
    List<Author> findAll();
    Author findById(long id);
}
