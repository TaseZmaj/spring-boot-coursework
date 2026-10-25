package org.example.auditoryexercise2.bootstrap;

import jakarta.annotation.PostConstruct;
import org.example.auditoryexercise2.model.Category;
import org.example.auditoryexercise2.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component //znachi deka ke se instancira samata klasa koga ke startuva aplikacijata
public class DataHolder {
    public static List<Category> categories = null;
    public static List<User> users = null;

    //init()
    // --> nema sam po sebe da se povika zatoa:
    @PostConstruct
    public void init(){
        categories = new ArrayList<>();
        categories.add(new Category("Software", "(Software Description)"));
        categories.add(new Category("Movies", "(Movies Description)"));
        categories.add(new Category("Books", "(Books Description)"));

        users = new ArrayList<>();
        users.add(new User("elena.atanasoska", "ea", "Elena", "Atanasoska"));
        users.add(new User("darko.sasanski", "ds", "Darko", "Sasanski"));
        users.add(new User("ana.todorovska", "at", "Ana", "Todorovska"));
    }
}
