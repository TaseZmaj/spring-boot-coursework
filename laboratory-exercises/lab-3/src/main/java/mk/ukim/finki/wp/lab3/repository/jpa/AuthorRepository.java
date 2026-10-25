package mk.ukim.finki.wp.lab3.repository.jpa;

import mk.ukim.finki.wp.lab3.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaSpecificationRepository<Author, Long> {
}
