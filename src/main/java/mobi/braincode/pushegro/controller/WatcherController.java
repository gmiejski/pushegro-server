package mobi.braincode.pushegro.controller;

import mobi.braincode.pushegro.domain.User;
import mobi.braincode.pushegro.repository.UserRepository;
import mobi.braincode.pushegro.domain.predicate.AuctionPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static java.lang.String.format;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@RestController
public class WatcherController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/{username}", method = RequestMethod.POST, consumes = "application/json")
    private String addWatcherForUser(@PathVariable String username, @RequestBody AuctionPredicate predicate) {
        User user = userRepository.loadUserByUsername(username);

        user.addWatcher(predicate);

        return format("Watcher added");
    }
}
