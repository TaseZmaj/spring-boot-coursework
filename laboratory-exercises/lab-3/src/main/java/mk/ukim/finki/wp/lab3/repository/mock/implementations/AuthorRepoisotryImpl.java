package mk.ukim.finki.wp.lab3.repository.mock.implementations;

import mk.ukim.finki.wp.lab3.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab3.model.Author;
import mk.ukim.finki.wp.lab3.repository.mock.AuthorRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthorRepoisotryImpl implements AuthorRepository {
    @Override
    public List<Author> findAll() {
        return DataHolder.authors;
    }

    @Override
    public Author findById(long id) {
        return DataHolder.authors.stream().filter(a->a.getId()==id).findFirst().orElse(null);
    }
}
