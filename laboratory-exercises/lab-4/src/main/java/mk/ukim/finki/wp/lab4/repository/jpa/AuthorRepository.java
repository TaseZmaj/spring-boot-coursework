package mk.ukim.finki.wp.lab4.repository.jpa;

import mk.ukim.finki.wp.lab4.model.Author;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaSpecificationRepository<Author, Long> {
}
