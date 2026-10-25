package mk.ukim.finki.wp.auditoryexercise4and5project.repository.implementations;

import mk.ukim.finki.wp.auditoryexercise4and5project.bootstrap.DataHolder;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.User;
import mk.ukim.finki.wp.auditoryexercise4and5project.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InMemoryUserRepository implements UserRepository {
    @Override
    public Optional<User> findByUsername(String username) {
        return DataHolder.users.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }

//    Ova e AUTHENTICATION Query
    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return DataHolder.users.stream().filter(u-> u.getUsername().equals(username) && u.getPassword().equals(password)).findFirst();
    }

    @Override
    public User save(User user) {
        //Remove existing user with the same username (prevent duplicates)
        DataHolder.users.removeIf(u->u.getUsername().equals(user.getUsername()));
        DataHolder.users.add(user);
        return user;
    }

    @Override
    public void deleteByUsername(String username) {
        DataHolder.users.removeIf(u->u.getUsername().equals(username));
    }
}