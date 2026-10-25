package mk.ukim.finki.wp.kol2025g2.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class SkiSlope {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer length;
    private SlopeDifficulty difficulty;

    @ManyToOne
    private SkiResort skiResort;

    private boolean closed = false;

    public SkiSlope(String name, Integer length, SlopeDifficulty difficulty, SkiResort skiResort, boolean closed) {
        this.name = name;
        this.length = length;
        this.difficulty = difficulty;
        this.skiResort = skiResort;
        this.closed = closed;
    }

    public SkiSlope(String name, Integer length, SlopeDifficulty difficulty, SkiResort skiResort) {
        this.name = name;
        this.length = length;
        this.difficulty = difficulty;
        this.skiResort = skiResort;
    }

    public Boolean isClosed(){
        return closed;
    }

}
