package mobi.braincode.pushegro.allegroapi.impl;

import mobi.braincode.pushegro.allegroapi.api.IWebApiFacade;
import mobi.braincode.pushegro.domain.auction.Auction;
import mobi.braincode.pushegro.domain.predicate.AuctionPredicate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@Component
public class WebApiFacade implements IWebApiFacade {

    private final AllegroWebApiClient client = new AllegroWebApiClient();

    @Override
    public Set<Auction> findAllAuctionByPredicate(AuctionPredicate predicate) {
        return client.loadAuctionsByPredicate(predicate);
    }
}
