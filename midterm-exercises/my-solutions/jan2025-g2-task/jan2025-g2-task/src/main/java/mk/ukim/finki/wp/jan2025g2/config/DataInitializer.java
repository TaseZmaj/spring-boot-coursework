package mk.ukim.finki.wp.jan2025g2.config;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.jan2025g2.model.ParkLocation;
import mk.ukim.finki.wp.jan2025g2.model.ParkType;
import mk.ukim.finki.wp.jan2025g2.service.NationalParkService;
import mk.ukim.finki.wp.jan2025g2.service.ParkLocationService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer {

    private final NationalParkService nationalParkService;
    private final ParkLocationService locationService;

    public DataInitializer(NationalParkService nationalParkService, ParkLocationService locationService) {
        this.nationalParkService = nationalParkService;
        this.locationService = locationService;
    }

    private ParkType randomizeParkType(int i) {
        if (i % 4 == 0) return ParkType.NATIONAL_RESERVE;
        if (i % 4 == 1) return ParkType.WILDLIFE_SANCTUARY;
        if (i % 4 == 2) return ParkType.MARINE_PROTECTED_AREA;
        return ParkType.BIOSPHERE_RESERVE;
    }

    @PostConstruct
    public void initData() {
        for (int i = 1; i <= 3; i++) {
            this.locationService.create("Country " + i, "Continent" + i);
        }

        List<ParkLocation> locations = this.locationService.listAll();

        for (int i = 1; i <= 10; i++) {
            String name = "National park " + i;
            Double areaSize = 500 + (i * 100.0);
            Double rating = 3.5 + (i * 0.2);
            ParkType parkType = this.randomizeParkType(i);
            Long locationId = locations.get((i - 1) % locations.size()).getId();

            this.nationalParkService.create(name, areaSize, rating, parkType, locationId);
        }
    }
}
