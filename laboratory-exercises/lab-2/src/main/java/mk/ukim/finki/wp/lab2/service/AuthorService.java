package mk.ukim.finki.wp.lab2.service;

import mk.ukim.finki.wp.lab2.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();
    Author findById(long id);
}
