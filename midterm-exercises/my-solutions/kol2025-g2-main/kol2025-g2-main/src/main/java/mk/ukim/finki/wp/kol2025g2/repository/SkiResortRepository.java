package mk.ukim.finki.wp.kol2025g2.repository;

import mk.ukim.finki.wp.kol2025g2.model.SkiResort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkiResortRepository extends JpaRepository<SkiResort, Long> {
}
