package mk.ukim.finki.wp.auditoryexercise4and5project.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Manufacturer {
    private long id;
    private String name;
    private String address;

    public Manufacturer(String name, String address){
        this.name = name;
        this.address = address;
        this.id = (long)(Math.random()*1000);
    }
}
