import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerUserPage implements Initializable {

    @FXML
    private VBox auctionCardsContainer;

    @FXML
    private Label userEmail;

    @FXML
    private Label userName;


    private BeanUser user;

    private AuctionManager auctionManager;

    private Main mainApplication;
    private BeanUser loggedInUser;

    public void setUser(BeanUser user) {
        this.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userName.setText(user.getName());
        userEmail.setText(user.getEmail());

        auctionManager = new AuctionManager();

        ArrayList<BeanAuction> auctions = auctionManager.getAuctionsByUser(user);

        displayAuctions(auctions);
    }



    private void displayAuctions(ArrayList<BeanAuction> auctions) {
        auctionCardsContainer.getChildren().clear();

        for (BeanAuction auction : auctions) {
            createAuctionCard(auction);
        }
    }

    private void createAuctionCard(BeanAuction auction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserPageCard.fxml"));
            Node auctionCard = loader.load();
            ControllerUserPageCard userPageCardController = loader.getController();
            userPageCardController.setUser(loggedInUser);
            userPageCardController.setAuctionData(auction);
            auctionCardsContainer.getChildren().add(auctionCard);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
