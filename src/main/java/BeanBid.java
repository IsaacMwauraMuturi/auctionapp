public class BeanBid {

    private BeanUser bidder;
    private double amount;

    /**
     * Constructor initializes the bid details.
     * 
     * @param bidder The user placing the bid.
     * @param amount The amount of the bid.
     */
    public BeanBid(BeanUser bidder, double amount) {
        this.bidder = bidder;
        this.amount = amount;
    }

    /**
     * Gets the bidder who placed the bid.
     * 
     * @return The bidder.
     */
    public BeanUser getBidder() {
        return bidder;
    }

    /**
     * Gets the amount of the bid.
     * 
     * @return The bid amount.
     */
    public double getAmount() {
        return amount;
    }
}
