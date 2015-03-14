package mobi.braincode.pushegro.domain.predicate;

import java.util.Set;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class AuctionPredicateList {
    private Set<AuctionPredicate> auctionPredicates;

    public AuctionPredicateList(Set<AuctionPredicate> auctionPredicates) {
        this.auctionPredicates = auctionPredicates;
    }

    public Set<AuctionPredicate> getAuctionPredicates() {
        return auctionPredicates;
    }
}
