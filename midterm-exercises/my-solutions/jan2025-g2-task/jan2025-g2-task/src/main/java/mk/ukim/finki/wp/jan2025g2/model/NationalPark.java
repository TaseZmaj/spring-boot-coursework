package mk.ukim.finki.wp.jan2025g2.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class NationalPark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double areaSize;
    private boolean closed = false;
    private Double rating;
    private ParkType parkType;

    @ManyToOne
    private ParkLocation location;

    public NationalPark(String name, Double areaSize, boolean closed, Double rating, ParkType parkType, ParkLocation location) {
        this.name = name;
        this.areaSize = areaSize;
        this.closed = closed;
        this.rating = rating;
        this.parkType = parkType;
        this.location = location;
    }

    public NationalPark(String name, Double areaSize, Double rating, ParkType parkType, ParkLocation location) {
        this.name = name;
        this.areaSize = areaSize;
        this.rating = rating;
        this.parkType = parkType;
        this.location = location;
    }

    public boolean isClosed(){
        return closed;
    }
}
