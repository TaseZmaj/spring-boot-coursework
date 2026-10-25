package mk.ukim.finki.wp.lab4.service.implementations;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.lab4.model.Author;
import mk.ukim.finki.wp.lab4.repository.jpa.AuthorRepository;
import mk.ukim.finki.wp.lab4.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }
}
