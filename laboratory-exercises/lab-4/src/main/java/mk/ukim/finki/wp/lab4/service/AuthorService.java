package mk.ukim.finki.wp.lab4.service;

import mk.ukim.finki.wp.lab4.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> findAll();
    Optional<Author> findById(Long id);
}
