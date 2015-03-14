package mobi.braincode.pushegro.domain;

import mobi.braincode.pushegro.GcmId;
import mobi.braincode.pushegro.domain.predicate.AuctionPredicate;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toSet;

public class User {

    @NotNull
    private String username;
    private GcmId gcmId;
    private Set<Watcher> watchers = new HashSet<>();

    public User() {
    }

    public User(String username, String gcmId) {
        this.username = username;
        this.gcmId = new GcmId(gcmId);
    }

    public AuctionPredicate addWatcher(AuctionPredicate predicate) {
        Optional<Watcher> first = watchers.stream()
                .filter(watcher -> watcher.getPredicate().equals(predicate))
                .findFirst();

        if (first.isPresent()) {
            return first.get().getPredicate();
        }
        watchers.add(new Watcher(predicate));

        return predicate;
    }

    public AuctionPredicate deleteWatcher(long predicateId) {
        Predicate<Watcher> predicate = watcher -> watcher.getPredicate().getPredicateId() == predicateId;

        Set<Watcher> toRemove = watchers.stream()
                .filter(predicate)
                .collect(toSet());

        this.watchers = watchers.stream()
                .filter(watcher -> watcher.getPredicate().getPredicateId() != predicateId)
                .collect(toSet());

        return toRemove.stream().findFirst().map(Watcher::getPredicate).orElse(null);
    }

    public Watcher loadWatcherByPredicateId(long predicate) {

        return watchers.stream()
                .filter(watcher -> watcher.getPredicate().getPredicateId() == predicate)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No matching predicate"));
    }

    public Set<Watcher> getWatchers() {
        return new HashSet<>(watchers);
    }

    public String getUsername() {
        return username;
    }

    public GcmId getGcmId() {
        return gcmId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (gcmId != null ? !gcmId.equals(user.gcmId) : user.gcmId != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (gcmId != null ? gcmId.hashCode() : 0);
        return result;
    }
}
