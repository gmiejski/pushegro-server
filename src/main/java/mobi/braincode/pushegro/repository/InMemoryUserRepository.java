package mobi.braincode.pushegro.repository;

import mobi.braincode.pushegro.domain.User;
import mobi.braincode.pushegro.domain.predicate.AuctionPredicate;
import mobi.braincode.pushegro.scheduler.ScheduledWatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Repository
public class InMemoryUserRepository implements UserRepository {

    @Autowired
    private ScheduledWatcher scheduledWatcher;

    private Set<User> users = new HashSet<>();

    public InMemoryUserRepository() {
    }

    @PostConstruct
    private void initWithDemoUsers() {
        User demoUserOne = new User("luk", "gcmId");
        AuctionPredicate auctionPredicateOne = new AuctionPredicate("dragon ball");
        User demoUserTwo = new User("grzesiek", "grzesiekId");
        AuctionPredicate auctionPredicateTwo = new AuctionPredicate("maczeta");

        users.add(demoUserOne);
        demoUserOne.addWatcher(auctionPredicateOne);
        scheduledWatcher.registerUser(demoUserOne);
        users.add(demoUserTwo);
        demoUserTwo.addWatcher(auctionPredicateTwo);
        scheduledWatcher.registerUser(demoUserTwo);
    }

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
}
