package mk.ukim.finki.wp.auditoryexercise4and5project.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category{
    private Long id;
    private String name;
    private String description;

    public Category(String name, String description){
        this.name = name;
        this.description = description;
        this.id = (long)(Math.random()*100);
    }
}
