package mobi.braincode.pushegro.repository;

import mobi.braincode.pushegro.domain.User;
import mobi.braincode.pushegro.domain.Watcher;
import mobi.braincode.pushegro.domain.predicate.AuctionPredicate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    private Map<User, Watcher> users = new HashMap<>();

    public UserRepository() {
        User demoUser = new User("luk", "gcmId");
        User grzesiekUser = new User("grzesiek", "grzesiekId");
        Watcher watcher = new Watcher();
        Watcher grzesiekWatcher = new Watcher(new AuctionPredicate("maczeta"));

        users.put(demoUser, watcher);
        users.put(grzesiekUser, grzesiekWatcher);
    }

    public void registerUser(User user) {
        if (users.containsKey(user)) {
            throw new IllegalStateException("user " + user.getUsername() + " is already registered");
        }
        users.put(user, new Watcher());
    }

    public User loadUserByUsername(String username) {
        for (User user : users.keySet()) {
            if (username.equalsIgnoreCase(user.getUsername())) {
                return user;
            }
        }
        return null;
    }
}
