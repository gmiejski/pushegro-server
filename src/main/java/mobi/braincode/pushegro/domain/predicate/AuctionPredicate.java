package mobi.braincode.pushegro.domain.predicate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class AuctionPredicate {
    private static long id = 0;

    @NotNull
    @NotEmpty
    private String keyword;

    @JsonIgnore
    private long predicateId = ++id;

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
