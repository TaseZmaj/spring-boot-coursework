package mk.ukim.finki.wp.lab4.repository.jpa;

import mk.ukim.finki.wp.lab4.model.Book;

import java.util.List;

public interface BookRepository extends JpaSpecificationRepository<Book,Long> {
    List<Book> findByAuthor_Id(Long author_id);
    List<Book> findByTitleContainingIgnoreCaseAndAverageRatingGreaterThanEqual(String title, double rating);
}
