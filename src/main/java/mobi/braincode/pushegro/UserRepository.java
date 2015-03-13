package mobi.braincode.pushegro;

import mobi.braincode.pushegro.domain.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    Map<GcmId, User> users = new HashMap<>();

    public void registerUser(User user) {
        if (users.containsKey(user.getGcmId())) {
            throw new IllegalStateException("user " + user.getUsername() + " is already registered");
        }
        users.put(user.getGcmId(), user);
    }
}
