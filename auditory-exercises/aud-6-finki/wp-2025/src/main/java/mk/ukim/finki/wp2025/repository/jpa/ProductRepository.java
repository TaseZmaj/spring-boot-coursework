package mk.ukim.finki.wp2025.repository.jpa;

import mk.ukim.finki.wp2025.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
