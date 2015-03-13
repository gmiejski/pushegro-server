package mobi.braincode.pushegro.domain.predicate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class AuctionPredicate {
    private static long id = 1;

    private String keyword;
    @JsonIgnore
    private long predicateId = id++;

    public AuctionPredicate() {
    }

    public AuctionPredicate(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    @JsonProperty
    public long getPredicateId() {
        return predicateId;
    }
}
