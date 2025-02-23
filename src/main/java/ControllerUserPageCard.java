import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.Optional;

public class ControllerUserPageCard {

    private BeanUser user;

    @FXML
    private Label auctionNameLabel;

    @FXML
    private Label auctionDescriptionLabel;

    @FXML
    private Label auctionStartingBidLabel;

    /**
     * Sets the user whose data is to be displayed.
     * 
     * @param user The user.
     */
    public void setUser(BeanUser user) {
        this.user = user;
    }

    /**
     * Sets the auction data to be displayed.
     * 
     * @param auction The auction whose data is to be displayed.
     */
    public void setAuctionData(BeanAuction auction) {
        AuctionManager auctionManager = new AuctionManager();
        ArrayList<BeanAuction> auctions = auctionManager.getAuctionsByUser(user);

        Optional<BeanAuction> foundAuction = auctions.stream()
                .filter(a -> a.getId() == auction.getId())
                .findFirst();

        if (foundAuction.isPresent()) {
            BeanAuction loadedAuction = foundAuction.get();
            auctionNameLabel.setText(loadedAuction.getName());
            auctionDescriptionLabel.setText(loadedAuction.getDescription());
            auctionStartingBidLabel.setText(String.valueOf(loadedAuction.getStartingBid()));
        } else {
            System.out.println("Auction not found in the loaded data.");
        }
    }
}
