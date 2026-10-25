package mk.ukim.finki.wp2025.service;

import mk.ukim.finki.wp2025.model.User;

public interface AuthService {
    User login(String username, String password);

    User register(String username, String password, String repeatPassword, String name, String surname);
}
