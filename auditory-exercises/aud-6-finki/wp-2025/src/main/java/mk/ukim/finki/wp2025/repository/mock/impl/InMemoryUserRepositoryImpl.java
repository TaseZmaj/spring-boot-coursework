package mk.ukim.finki.wp2025.repository.mock.impl;

import mk.ukim.finki.wp2025.bootstrap.DataHolder;
import mk.ukim.finki.wp2025.model.User;
import mk.ukim.finki.wp2025.repository.mock.InMemoryUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InMemoryUserRepositoryImpl implements InMemoryUserRepository {

    @Override
    public Optional<User> findByUsername(String username) {
        return DataHolder.users.stream()
                               .filter(u -> u.getUsername().equals(username))
                               .findFirst();
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return DataHolder.users.stream()
                               .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                               .findFirst();
    }

    @Override
    public User save(User user) {
        // Remove any existing user with the same username before adding (prevents duplicates)
        DataHolder.users.removeIf(u -> u.getUsername().equals(user.getUsername()));
        DataHolder.users.add(user);
        return user;
    }

    @Override
    public void deleteByUsername(String username) {
        DataHolder.users.removeIf(u -> u.getUsername().equals(username));
    }
}
