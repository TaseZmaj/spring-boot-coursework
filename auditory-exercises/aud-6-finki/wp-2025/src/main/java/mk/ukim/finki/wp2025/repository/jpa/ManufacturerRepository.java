package mk.ukim.finki.wp2025.repository.jpa;

import mk.ukim.finki.wp2025.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
}
