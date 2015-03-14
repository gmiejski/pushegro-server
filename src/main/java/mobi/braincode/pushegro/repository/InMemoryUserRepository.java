package mobi.braincode.pushegro.repository;

import mobi.braincode.pushegro.domain.User;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private Set<User> users = new HashSet<>();

    @Override
    public void registerUser(User user) {
        if (users.contains(user)) {
            throw new IllegalStateException("user " + user.getUsername() + " is already registered");
        }
        users.add(user);
    }

    @Override
    public User loadUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No such user in repository"));
    }


    public Set<User> getUsers() {
        return users;
    }
}
