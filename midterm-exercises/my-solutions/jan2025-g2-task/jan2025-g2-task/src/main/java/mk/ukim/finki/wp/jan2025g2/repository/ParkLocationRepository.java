package mk.ukim.finki.wp.jan2025g2.repository;

import mk.ukim.finki.wp.jan2025g2.model.ParkLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkLocationRepository extends JpaRepository<ParkLocation,Long> {
}
