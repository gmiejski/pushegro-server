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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuctionPredicate that = (AuctionPredicate) o;

        if (predicateId != that.predicateId) return false;
        if (keyword != null ? !keyword.equals(that.keyword) : that.keyword != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = keyword != null ? keyword.hashCode() : 0;
        result = 31 * result + (int) (predicateId ^ (predicateId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "AuctionPredicate{" +
                "keyword='" + keyword + '\'' +
                ", predicateId=" + predicateId +
                '}';
    }
}
