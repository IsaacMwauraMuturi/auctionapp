import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

public class ControllerAuctionPage {
    private BeanAuction auction;

    private BeanUser seller;

    public BeanUser loggedInUser;

    private ControllerAuction auctionController;

    private ControllerAuctionCard auctionCardController;

    private Main main;

    @FXML
    private Label auctionNameLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label sellerName;

    @FXML
    private Label endTime;

    @FXML
    private Label currentBid;

    @FXML
    private Button bidButton;

    @FXML
    private TextField bidAmountField;

    @FXML
    private Label highestBidLabel;

    @FXML
    private Label winnerLabel;

    @FXML
    private Button backButton;

    public void setMain(Main main) {
        this.main = main;
    }

    public void setLoggedInUser(BeanUser user) {
        this.loggedInUser=user;
        this.seller = user;
    }

    public void setAuctionCardController(ControllerAuctionCard auctionCardController) {
        this.auctionCardController = auctionCardController;

    }

    public void setAuctionController(ControllerAuction auctionController) {
        this.auctionController = auctionController;
    }

    public void setAuction(BeanAuction auction) {
        this.auction = auction;

        endTime.setText(String.valueOf(auction.getEndTime()));
        auctionNameLabel.setText(auction.getName());
        descriptionLabel.setText(auction.getDescription());
        sellerName.setText(auction.getSeller().getName());
        System.out.println(String.valueOf(auction.getStartingBid()));
        highestBidLabel.setText(String.valueOf(auction.getStartingBid()));
    }

    @FXML
    private void handleBidButtonClicked(ActionEvent event) throws BeanAuction.InvalidBidException {
        seller = loggedInUser;
        System.out.println(auctionCardController.getSelectedAuction());

        if (auctionCardController.getSelectedAuction() == null) {
            System.out.println("No auction selected.");
            return;
        }

        System.out.println("seller: " + seller);

        if (seller != null) {

            double bidAmount = Double.parseDouble(bidAmountField.getText());

            auctionCardController.getSelectedAuction().placeBid(seller, bidAmount);

            updateHighestBidLabel();

            bidAmountField.clear();
        }else {
            System.out.println("null");
        }

    }

    @FXML
    private void handleBackButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("auctions_page.fxml"));
            Parent root = loader.load();

            System.out.println("user: " + loggedInUser);
            System.out.println("main: " + main);

            ControllerAuctionsPage auctionsPageController = loader.getController();
            auctionsPageController.setLoggedInUser(loggedInUser);
            auctionsPageController.setMainApplication(main);

            Scene scene = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ControllerUserPage userPageController;

    @FXML
    private void UserPage(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserPage.fxml"));
            Parent root = loader.load();
            userPageController = loader.getController();
            System.out.println("eee");
            userPageController.setUser(seller);

            main.setScene(root);
        }catch (Exception e){
            System.out.println(e);
        }
    }



    private void updateHighestBidLabel() {
        if (auctionCardController.getSelectedAuction() != null && !auctionCardController.getSelectedAuction().getBids().isEmpty()) {
            BeanBid highestBid = Collections.max(auctionCardController.getSelectedAuction().getBids(), Comparator.comparingDouble(BeanBid::getAmount));
            highestBidLabel.setText(String.valueOf(highestBid.getAmount()));
        }
    }

    private void updateWinnerLabel() {
        if (auctionCardController.getSelectedAuction() != null) {
            BeanUser winner = auctionCardController.getSelectedAuction().getWinner();
            if (winner != null) {
                winnerLabel.setText(winner.getUsername());
            } else {
                winnerLabel.setText("No winner");
            }
        }
    }


}
