package mk.ukim.finki.wp.kol2025g2.config;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.kol2025g2.model.SlopeDifficulty;
import mk.ukim.finki.wp.kol2025g2.service.SkiResortService;
import mk.ukim.finki.wp.kol2025g2.service.SkiSlopeService;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final SkiResortService skiResortService;
    private final SkiSlopeService skiSlopeService;

    public DataInitializer(SkiResortService skiResortService, SkiSlopeService skiSlopeService) {
        this.skiResortService = skiResortService;
        this.skiSlopeService = skiSlopeService;
    }

    private SlopeDifficulty randomizeDifficulty(int i) {
        if (i % 4 == 0) return SlopeDifficulty.BLUE;
        if (i % 4 == 1) return SlopeDifficulty.RED;
        if (i % 4 == 2) return SlopeDifficulty.BLACK;
        return SlopeDifficulty.GREEN;
    }

    @PostConstruct
    public void initData() {
        for (int i = 1; i <= 3; i++) {
            this.skiResortService.create("Ski Resort " + i, "Location " + i);
        }

        var skiResorts = this.skiResortService.listAll();
        for (int i = 1; i <= 10; i++) {
            String name = "Slope " + i;
            int length = 20 + (i * 10);
            SlopeDifficulty difficulty = this.randomizeDifficulty(i);
            Long resortId = skiResorts.get((i - 1) % skiResorts.size()).getId();

            this.skiSlopeService.create(name, length, difficulty, resortId);
        }
    }
}
