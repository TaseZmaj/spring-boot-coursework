package mk.ukim.finki.wp2025.repository.mock;

import mk.ukim.finki.wp2025.model.User;

import java.util.Optional;

public interface InMemoryUserRepository {

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);

    User save(User user);

    void deleteByUsername(String username);
}
