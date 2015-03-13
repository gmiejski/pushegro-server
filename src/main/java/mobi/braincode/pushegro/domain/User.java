package mobi.braincode.pushegro.domain;

import mobi.braincode.pushegro.GcmId;
import mobi.braincode.pushegro.domain.predicate.AuctionPredicate;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class User {

    private String username;
    private GcmId gcmId;
    private Set<Watcher> watchers = new HashSet<>();

    public User() {
    }

    public User(String username, String gcmId) {
        this.username = username;
        this.gcmId = new GcmId(gcmId);
    }

    public void addWatcher(AuctionPredicate predicate) {
        Optional<Watcher> first = watchers.stream().filter(watcher -> watcher.getPredicate().equals(predicate)).findFirst();
        if (first.isPresent()) {
            return;
        }
        watchers.add(new Watcher(predicate));
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
