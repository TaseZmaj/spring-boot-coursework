package mk.ukim.finki.wp2025.service.impl;

import mk.ukim.finki.wp2025.model.User;
import mk.ukim.finki.wp2025.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.wp2025.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.wp2025.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.wp2025.repository.jpa.UserRepository;
import mk.ukim.finki.wp2025.repository.mock.InMemoryUserRepository;
import mk.ukim.finki.wp2025.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }

        return this.userRepository.findByUsernameAndPassword(username, password)
                                  .orElseThrow(InvalidUserCredentialsException::new);
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname) {
        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty() ||
                repeatPassword == null || repeatPassword.isEmpty() ||
                name == null || name.isEmpty() ||
                surname == null || surname.isEmpty()
        ) {
            throw new InvalidArgumentsException();
        }

        if (!password.equals(repeatPassword)) {
            throw new PasswordsDoNotMatchException();
        }

        return this.userRepository.save(new User(username, password, name, surname));
    }
}
