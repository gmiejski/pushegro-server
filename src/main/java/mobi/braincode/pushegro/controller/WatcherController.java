package mobi.braincode.pushegro.controller;

import mobi.braincode.pushegro.domain.Auction;
import mobi.braincode.pushegro.domain.User;
import mobi.braincode.pushegro.domain.Watcher;
import mobi.braincode.pushegro.repository.UserRepository;
import mobi.braincode.pushegro.domain.predicate.AuctionPredicate;
import mobi.braincode.pushegro.gcm.GcmNotifier;
import mobi.braincode.pushegro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

import java.util.Set;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@RestController
public class WatcherController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GcmNotifier gcmNotifier;

    @RequestMapping(value = "/{username}", method = RequestMethod.POST, consumes = "application/json")
    public String addWatcherForUser(@PathVariable String username, @RequestBody AuctionPredicate predicate) {
        User user = userRepository.loadUserByUsername(username);

        user.addWatcher(predicate);

        return format("Watcher added");
    }

    @RequestMapping(value = "/{username}/notify", method = RequestMethod.POST, consumes = "application/json")
    public String notifyUser(@PathVariable String username, @RequestBody String predicates) {
        User user = userRepository.loadUserByUsername(username);

        List<String> predicatesChanged = Stream.of(predicates.split(",")).collect(toList());

        gcmNotifier.notify(user, predicatesChanged);
        return "User " + user.getUsername() + " notified!";
    }

    @RequestMapping(value = "/{username}/{predicateId}", method = RequestMethod.GET, produces = "application/json")
    public Set<Auction> loadAuctionsMatchingPredicate(@PathVariable String username, @PathVariable long predicateId) {
        User user = userRepository.loadUserByUsername(username);
        Watcher watcher = user.loadWatcherByPredicateId(predicateId);

        return watcher.getMatchingAuctions();
    }
}
