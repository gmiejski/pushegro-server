package mobi.braincode.pushegro.domain.auction;

import java.util.Set;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class AuctionList {
    private final Set<Auction> auctions;

    public AuctionList(Set<Auction> auctions) {
        this.auctions = auctions;
    }
}
