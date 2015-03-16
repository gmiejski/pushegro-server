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

    final String ALLEGRO_USERNAME = System.getenv("ALLEGRO_USERNAME");
    final String ALLEGRO_PASSWORD_BASE64 = System.getenv("ALLEGRO_PASSWORD_BASE64");
    final String ALLEGRO_KEY = System.getenv("ALLEGRO_KEY");

    private final AllegroWebApiClient client = new AllegroWebApiClient(ALLEGRO_USERNAME, ALLEGRO_PASSWORD_BASE64, ALLEGRO_KEY);

    @Override
    public Set<Auction> findAllAuctionByPredicate(AuctionPredicate predicate) {
        return client.loadAuctionsByPredicate(predicate);
    }
}
