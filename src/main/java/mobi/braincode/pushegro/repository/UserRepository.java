package mobi.braincode.pushegro.repository;

import mobi.braincode.pushegro.domain.User;

import java.util.Set;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public interface UserRepository {
    void registerUser(User user);

    User loadUserByUsername(String username);

    Set<User> getUsers();

}
