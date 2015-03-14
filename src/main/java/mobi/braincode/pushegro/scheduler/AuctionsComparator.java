package mobi.braincode.pushegro.scheduler;

import mobi.braincode.pushegro.domain.Auction;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@Component
public class AuctionsComparator {

    public boolean areEqual(Set<Auction> old, Set<Auction> newOne) {
        return old.equals(newOne);
    }
}
