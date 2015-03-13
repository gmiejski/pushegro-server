package mobi.braincode.pushegro.domain;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class Auction {
    private long auctionId;
    private String title;

    public Auction(long auctionId, String title) {
        this.auctionId = auctionId;
        this.title = title;
    }

    public long getAuctionId() {
        return auctionId;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Auction auction = (Auction) o;

        if (auctionId != auction.auctionId) return false;
        if (title != null ? !title.equals(auction.title) : auction.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (auctionId ^ (auctionId >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
