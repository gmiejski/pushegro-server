package mobi.braincode.pushegro.repository;

import mobi.braincode.pushegro.domain.User;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public interface UserRepository {
    void registerUser(User user);

    User loadUserByUsername(String username);
}
