package mk.ukim.finki.wp.jan2025g1.repository;

import mk.ukim.finki.wp.jan2025g1.model.SiteLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteLocationRepository extends JpaRepository<SiteLocation, Long> {
}
