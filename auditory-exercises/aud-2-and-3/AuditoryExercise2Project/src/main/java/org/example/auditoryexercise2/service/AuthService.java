package org.example.auditoryexercise2.service;

import org.example.auditoryexercise2.model.User;

public interface AuthService {
    User login(String username, String password);
    User register(String username, String password, String repeatPassword, String name, String surname);
}

