package mk.ukim.finki.wp.auditoryexercise4and5project.service;

import mk.ukim.finki.wp.auditoryexercise4and5project.model.User;

public interface AuthService {
    User login(String username, String password);
    User register(String username, String password, String repeatPassword, String name, String surname);
}

