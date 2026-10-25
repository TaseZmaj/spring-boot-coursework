package mk.ukim.finki.wp.lab4.repository.mock;

import mk.ukim.finki.wp.lab4.model.Author;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository {
    List<Author> findAll();
    Author findById(long id);
}
