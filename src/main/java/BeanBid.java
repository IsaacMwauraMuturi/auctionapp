public class BeanBid {
    private BeanUser bidder;
    private double amount;

    public BeanBid(BeanUser bidder, double amount) {
        this.bidder = bidder;
        this.amount = amount;
    }

    public BeanUser getBidder() {
        return bidder;
    }

    public double getAmount() {
        return amount;
    }
}
