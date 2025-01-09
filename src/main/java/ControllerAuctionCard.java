import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class ControllerAuctionCard {

    private BeanUser user;
    private BeanAuction selectedAuction;
    private Main mainApplication;
    private ControllerAuction auctionController;

    /**
     * Sets the auction controller instance.
     * 
     * @param auctionController The auction controller instance.
     */
    public void setAuctionController(ControllerAuction auctionController) {
        this.auctionController = auctionController;
    }

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label auctionNameLabel;

    @FXML
    private Label auctionDescriptionLabel;

    @FXML
    private Label auctionStartingBidLabel;

    /**
     * Sets the main application instance.
     * 
     * @param mainApplication The main application instance.
     */
    public void setMainApplication(Main mainApplication) {
        this.mainApplication = mainApplication;
    }

    /**
     * Sets the logged-in user.
     * 
     * @param loggedInUser The logged-in user.
     */
    public void setUser(BeanUser loggedInUser) {
        this.user = loggedInUser;
    }

    /**
     * Sets the auction data to be displayed.
     * 
     * @param auction The auction whose data is to be displayed.
     */
    public void setAuctionData(BeanAuction auction) {
        AuctionManager auctionManager = new AuctionManager();
        ArrayList<BeanAuction> auctions = auctionManager.getActiveAuctions();

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

    /**
     * Sets the selected auction.
     * 
     * @param auction The selected auction.
     */
    public void setSelectedAuction(BeanAuction auction) {
        selectedAuction = auction;
    }

    /**
     * Gets the selected auction.
     * 
     * @return The selected auction.
     */
    public BeanAuction getSelectedAuction() {
        return selectedAuction;
    }

    /**
     * Handles the auction click event.
     * 
     * @param event The mouse event.
     */
    @FXML
    private void handleAuctionClick(MouseEvent event) {
        if (selectedAuction == null) {
            System.out.println("No auction selected.");
            return;
        }

        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("auction_page.fxml"));
            Parent root = loader.load();

            ControllerAuctionPage auctionPageController = loader.getController();
            auctionPageController.setMain(mainApplication);
            auctionPageController.setAuctionController(auctionController);
            auctionPageController.setAuctionCardController(this);
            auctionPageController.setAuction(selectedAuction);
            auctionPageController.setLoggedInUser(user);
            System.out.println(selectedAuction.getName());

            Scene auctionPageScene = new Scene(root);
            currentStage.setScene(auctionPageScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
