package mk.ukim.finki.wp.lab2.service.implementations;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.lab2.model.Author;
import mk.ukim.finki.wp.lab2.repository.AuthorRepository;
import mk.ukim.finki.wp.lab2.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author findById(long id) {
        return authorRepository.findById(id);
    }
}
