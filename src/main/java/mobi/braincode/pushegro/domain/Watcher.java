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

        if (predicate != null ? !predicate.equals(watcher.predicate) : watcher.predicate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return predicate != null ? predicate.hashCode() : 0;
    }
}
