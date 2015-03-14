package mobi.braincode.pushegro.domain;

import mobi.braincode.pushegro.domain.auction.Auction;
import mobi.braincode.pushegro.domain.predicate.AuctionPredicate;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class Watcher {

    private AuctionPredicate predicate;
    private Set<Auction> matchingAuctions = new HashSet<>();

    public Watcher() {
    }

    public Watcher(AuctionPredicate predicate) {
        this.predicate = predicate;
    }

    public AuctionPredicate getPredicate() {
        return predicate;
    }

    public void updateMatchingAuctions(Set<Auction> auctions) {
        this.matchingAuctions = auctions;
    }

    public Set<Auction> getMatchingAuctions() {
        return new HashSet<>(matchingAuctions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Watcher watcher = (Watcher) o;

        if (matchingAuctions != null ? !matchingAuctions.equals(watcher.matchingAuctions) : watcher.matchingAuctions != null)
            return false;
        if (predicate != null ? !predicate.equals(watcher.predicate) : watcher.predicate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = predicate != null ? predicate.hashCode() : 0;
        result = 31 * result + (matchingAuctions != null ? matchingAuctions.hashCode() : 0);
        return result;
    }
}
