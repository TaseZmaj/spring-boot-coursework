package mk.ukim.finki.wp2025.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "manufacturer_address" )
    private String address;

    public Manufacturer(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
