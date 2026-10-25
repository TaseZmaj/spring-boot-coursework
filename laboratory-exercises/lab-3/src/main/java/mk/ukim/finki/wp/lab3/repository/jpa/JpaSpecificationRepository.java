package mk.ukim.finki.wp.lab3.repository.jpa;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean  // Don't create bean for this interface
public interface JpaSpecificationRepository<T, ID> extends JpaRepository<T, ID> {
    List<T> findAll(Specification<T> filter);
}

