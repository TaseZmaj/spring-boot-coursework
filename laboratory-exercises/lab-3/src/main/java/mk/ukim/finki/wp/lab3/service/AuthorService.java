package mk.ukim.finki.wp.lab3.service;

import mk.ukim.finki.wp.lab3.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> findAll();
    Optional<Author> findById(Long id);
}
