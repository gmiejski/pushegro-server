package mobi.braincode.pushegro.scheduler;

import mobi.braincode.pushegro.allegroapi.api.IWebApiFacade;
import mobi.braincode.pushegro.domain.Auction;
import mobi.braincode.pushegro.domain.User;
import mobi.braincode.pushegro.domain.Watcher;
import mobi.braincode.pushegro.gcm.GcmNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@Component
public class ScheduledWatcher {

    private final GcmNotifier notifier;
    private final IWebApiFacade apiFacade;
    private final AuctionsComparator comparator;

    private final Set<User> scheduledUsers = new HashSet<>();

    @Autowired
    public ScheduledWatcher(GcmNotifier notifier, IWebApiFacade apiFacade, AuctionsComparator comparator) {
        this.notifier = notifier;
        this.apiFacade = apiFacade;
        this.comparator = comparator;
    }

    public void registerUser(User user) {
        scheduledUsers.add(user);
        refreshWatchesAndNotifyMobiles();
    }

    public void refreshWatchesAndNotifyMobiles() {
        Map<User, List<Long>> notifications = refreshWatches();

        notifications.forEach(notifier::notify);
    }

    private Map<User, List<Long>> refreshWatches() {
        Map<User, List<Long>> notifications = new HashMap<>();

        for (User scheduledUser : scheduledUsers) {
            for (Watcher watcher : scheduledUser.getWatchers()) {
                Set<Auction> freshAuctions = apiFacade.findAllAuctionByPredicate(watcher.getPredicate());
                if (!comparator.areEqual(watcher.getMatchingAuctions(), freshAuctions)) {
                    addPredicateIdToNotifications(notifications, scheduledUser, watcher);
                    watcher.updateMatchingAuctions(freshAuctions);
                }
            }
        }
        return notifications;
    }

    private void addPredicateIdToNotifications(Map<User, List<Long>> notifications, User scheduledUser, Watcher watcher) {
        notifications.putIfAbsent(scheduledUser, new ArrayList<>());

        List<Long> predicateIds = notifications.get(scheduledUser);
        predicateIds.add(watcher.getPredicate().getPredicateId());
    }
}
