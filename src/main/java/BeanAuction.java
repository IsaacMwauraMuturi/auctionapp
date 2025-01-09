import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BeanAuction {
    private final int id;
    private final String name;
    private final String description;
    private final double startingBid;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final BeanUser seller;
    private BeanUser highestBidder;
    private double highestBid;
    private final ArrayList<BeanBid> bids;
    private boolean ended;

    /**
     * Constructor initializes the auction details.
     * 
     * @param id The auction id.
     * @param name The name of the auction.
     * @param description The description of the auction.
     * @param startingBid The starting bid of the auction.
     * @param startTime The start time of the auction.
     * @param endTime The end time of the auction.
     * @param seller The user who is selling the item.
     * @param bids The list of bids placed on the auction.
     * @param ended The status of whether the auction has ended.
     */
    public BeanAuction(int id, String name, String description, double startingBid, LocalDateTime startTime, LocalDateTime endTime, BeanUser seller, ArrayList<BeanBid> bids, boolean ended) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startingBid = startingBid;
        this.startTime = startTime;
        this.endTime = endTime;
        this.seller = seller;
        this.highestBid = startingBid;
        this.bids = bids;
        this.ended = false;
    }

    /**
     * Gets the auction id.
     * 
     * @return The auction id.
     */
    public int getId() {
        return id;
    }

    /**
     * Checks if the auction has ended.
     * 
     * @return True if the auction has ended, otherwise false.
     */
    public boolean isEnded() {
        return ended;
    }

    /**
     * Sets the auction status to ended.
     * 
     * @param ended The new ended status.
     */
    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    /**
     * Gets the auction name.
     * 
     * @return The auction name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the auction description.
     * 
     * @return The auction description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the starting bid of the auction.
     * 
     * @return The starting bid.
     */
    public double getStartingBid() {
        return startingBid;
    }

    /**
     * Gets the start time of the auction.
     * 
     * @return The start time.
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Gets the end time of the auction.
     * 
     * @return The end time.
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Gets the seller of the auction.
     * 
     * @return The seller.
     */
    public BeanUser getSeller() {
        return seller;
    }

    /**
     * Gets the highest bidder of the auction.
     * 
     * @return The highest bidder.
     */
    public BeanUser getHighestBidder() {
        return highestBidder;
    }

    /**
     * Gets the highest bid amount.
     * 
     * @return The highest bid.
     */
    public double getHighestBid() {
        return highestBid;
    }

    /**
     * Gets the list of bids placed on the auction.
     * 
     * @return The list of bids.
     */
    public ArrayList<BeanBid> getBids() {
        return bids;
    }

    /**
     * Exception class for invalid bids.
     */
    public static class InvalidBidException extends Exception {
        public InvalidBidException(String message) {
            super(message);
        }
    }

    /**
     * Places a bid on the auction.
     * 
     * @param bidder The user placing the bid.
     * @param amount The amount of the bid.
     * @throws InvalidBidException if the bid is invalid.
     */
    public void placeBid(BeanUser bidder, double amount) throws InvalidBidException {
        if (amount <= highestBid) {
            throw new InvalidBidException("Bid must be higher than the current highest bid.");
        }

        if (bidder.equals(seller)) {
            throw new InvalidBidException("Seller cannot bid on their own auction.");
        }

        if (isEnded()) {
            throw new InvalidBidException("Auction has already ended.");
        }

        BeanBid bid = new BeanBid(bidder, amount);
        bids.add(bid);
        highestBidder = bidder;
        highestBid = amount;
    }

    /**
     * Checks if the auction has expired.
     * 
     * @return True if the auction has expired, otherwise false.
     */
    public boolean isExpired() {
        if (!ended && LocalDateTime.now().isAfter(endTime)) {
            ended = true;
        }
        return ended;
    }

    /**
     * Checks if the auction is active.
     * 
     * @return True if the auction is active, otherwise false.
     */
    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(startTime) && now.isBefore(endTime);
    }

    /**
     * Returns a string representation of the auction.
     * 
     * @return The auction details as a string.
     */
    @Override
    public String toString() {
        LocalDateTime currentTime = LocalDateTime.now();
        boolean isActive = currentTime.isBefore(endTime);

        return "Auction: " + name + "\n" +
                "Description: " + description + "\n" +
                "Starting bid: " + startingBid + "\n" +
                "Current highest bid: " + highestBid + "\n" +
                "Seller: " + seller.getUsername() + "\n" +
                "Highest bidder: " + (highestBidder != null ? highestBidder.getUsername() : "none") + "\n" +
                "Start time: " + startTime + "\n" +
                "End time: " + endTime + "\n" +
                "Auction active or not: " + isActive + "\n";
    }

    /**
     * Gets the winner of the auction.
     * 
     * @return The winner of the auction.
     */
    public BeanUser getWinner() {
        if (bids.isEmpty()) {
            return null; // No winner if no bids
        }

        BeanBid highestBid = Collections.max(bids, Comparator.comparingDouble(BeanBid::getAmount));
        return highestBid.getBidder();
    }

}
