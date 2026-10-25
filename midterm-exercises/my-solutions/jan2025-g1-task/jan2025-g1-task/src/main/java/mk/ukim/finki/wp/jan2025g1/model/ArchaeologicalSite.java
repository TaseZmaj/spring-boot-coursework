package mk.ukim.finki.wp.jan2025g1.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ArchaeologicalSite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double areaSize;
    private boolean closed = false;
    private Double rating;
    private HistoricalPeriod period;

    @ManyToOne
    private SiteLocation location;

    public ArchaeologicalSite(String name, Double areaSize, boolean closed, Double rating, HistoricalPeriod period, SiteLocation location) {
        this.name = name;
        this.areaSize = areaSize;
        this.closed = closed;
        this.rating = rating;
        this.period = period;
        this.location = location;
    }

    public ArchaeologicalSite(String name, Double areaSize, Double rating, HistoricalPeriod period, SiteLocation location) {
        this.name = name;
        this.areaSize = areaSize;
        this.rating = rating;
        this.period = period;
        this.location = location;
    }

    public boolean isClosed(){
        return this.closed;
    }
}

