package mobi.braincode.pushegro.allegroapi.api;

import mobi.braincode.pushegro.domain.Auction;
import mobi.braincode.pushegro.domain.AuctionPredicate;

import java.util.Set;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public interface IWebApiFacade {
    Set<Auction> findAllAuctionByPredicate(AuctionPredicate predicate);
}
