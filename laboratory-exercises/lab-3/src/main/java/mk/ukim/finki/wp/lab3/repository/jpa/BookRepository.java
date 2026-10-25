package mk.ukim.finki.wp.lab3.repository.jpa;

import mk.ukim.finki.wp.lab3.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaSpecificationRepository<Book,Long> {
    List<Book> findByAuthor_Id(Long author_id);
    List<Book> findByTitleContainingIgnoreCaseAndAverageRatingGreaterThanEqual(String title, double rating);
}
