package mobi.braincode.pushegro.controller;

import mobi.braincode.pushegro.domain.User;
import mobi.braincode.pushegro.domain.Watcher;
import mobi.braincode.pushegro.domain.auction.AuctionList;
import mobi.braincode.pushegro.domain.predicate.AuctionPredicate;
import mobi.braincode.pushegro.domain.predicate.AuctionPredicateList;
import mobi.braincode.pushegro.gcm.GcmNotifier;
import mobi.braincode.pushegro.repository.UserRepository;
import mobi.braincode.pushegro.scheduler.ScheduledWatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@RestController
@RequestMapping("/{username}")
public class WatcherController {

    private UserRepository userRepository;
    private GcmNotifier gcmNotifier;
    private ScheduledWatcher scheduledWatcher;

    @Autowired
    public WatcherController(UserRepository userRepository, GcmNotifier gcmNotifier, ScheduledWatcher scheduledWatcher) {
        this.userRepository = userRepository;
        this.gcmNotifier = gcmNotifier;
        this.scheduledWatcher = scheduledWatcher;
    }

    @RequestMapping(value = "/predicates", method = RequestMethod.POST, consumes = "application/json")
    public AuctionPredicate addWatcherForUser(@PathVariable String username, @RequestBody @Valid AuctionPredicate predicate) {
        User user = userRepository.loadUserByUsername(username);
        scheduledWatcher.refreshWatchesAndNotifyMobiles();

        return user.addWatcher(predicate);
    }

    @RequestMapping(value = "/predicates", method = RequestMethod.GET, produces = "application/json")
    public AuctionPredicateList getAllPredicates(@PathVariable String username) {
        User user = userRepository.loadUserByUsername(username);
        Set<AuctionPredicate> auctions = user.getWatchers()
                .stream()
                .map(Watcher::getPredicate)
                .collect(toSet());
        return new AuctionPredicateList(auctions);
    }

    @RequestMapping(value = "/predicates/{predicateId}", method = RequestMethod.GET, produces = "application/json")
    public AuctionList loadAuctionsMatchingPredicate(@PathVariable String username, @PathVariable long predicateId) {
        User user = userRepository.loadUserByUsername(username);
        Watcher watcher = user.loadWatcherByPredicateId(predicateId);

        return new AuctionList(watcher.getMatchingAuctions());
    }

    @RequestMapping(value = "/predicates/{predicateId}", method = RequestMethod.DELETE)
    public AuctionPredicate deletePredicate(@PathVariable String username, @PathVariable long predicateId) {
        User user = userRepository.loadUserByUsername(username);

        return user.deleteWatcher(predicateId);
    }

    @RequestMapping(value = "/notify", method = RequestMethod.POST, consumes = "application/json")
    public String notifyUser(@PathVariable @NotNull String username, @RequestBody String predicates) {
        User user = userRepository.loadUserByUsername(username);

        List<Long> ids = Stream.of(predicates.split(",")).map(Long::valueOf).collect(toList());

        gcmNotifier.notify(user, ids);
        return "User " + user.getUsername() + " notified!";
    }
}
