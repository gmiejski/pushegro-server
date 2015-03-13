package mobi.braincode.pushegro.allegroapi.impl;

import mobi.braincode.pushegro.allegroapi.api.IWebApiFacade;
import mobi.braincode.pushegro.domain.Auction;
import mobi.braincode.pushegro.domain.AuctionPredicate;

import java.util.Collections;
import java.util.Set;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class WebApiFacade implements IWebApiFacade {
    @Override
    public Set<Auction> findAllAuctionByPredicate(AuctionPredicate predicate) {
        return Collections.emptySet();
    }
}
