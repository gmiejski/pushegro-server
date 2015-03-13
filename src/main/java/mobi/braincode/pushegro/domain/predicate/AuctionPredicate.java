package mobi.braincode.pushegro.domain.predicate;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class AuctionPredicate {
    private String keyword;

    public AuctionPredicate(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

}
