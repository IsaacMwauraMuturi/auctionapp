import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerAuctionsPage implements Initializable {


    private Main mainApplication;

    public BeanUser loggedInUser;


    @FXML
    private Button exitButton;

    @FXML
    private VBox auctionCardsContainer;

    @FXML
    private TextField searchField;

    private AuctionManager auctionManager;

    public void setLoggedInUser(BeanUser user) {
        this.loggedInUser = user;
    }

    public void setMainApplication(Main mainApplication) {
        this.mainApplication = mainApplication;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        auctionManager = new AuctionManager();

        ArrayList<BeanAuction> auctions = auctionManager.getActiveAuctions();

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("auction_card.fxml"));
            Node auctionCard = loader.load();
            ControllerAuctionCard auctionCardController = loader.getController();
            auctionCardController.setMainApplication(this.mainApplication);
            auctionCardController.setUser(loggedInUser);
            auctionCardController.setAuctionData(auction);
            auctionCardController.setSelectedAuction(auction);
            auctionCardsContainer.getChildren().add(auctionCard);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void quit() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleSearch() {
        String searchQuery = searchField.getText();

        ArrayList<BeanAuction> searchResults = auctionManager.searchAuctions(searchQuery);

        displayAuctions(searchResults);
    }

    @FXML
    private void handleCreateAuction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("create_auction.fxml"));
            Parent root = loader.load();
            ControllerAuction createAuctionController = loader.getController();
            createAuctionController.setLoggedInUser(loggedInUser);
            createAuctionController.setMain(mainApplication);
            mainApplication.setScene(root);
        } catch (Exception e) {
            System.out.println(e + "error");
        }

        List<BeanAuction> auctions = auctionManager.getActiveAuctions();
        List<Node> auctionItems = new ArrayList<>();

        for (BeanAuction auction : auctions) {

            auctionCardsContainer.setUserData(auction);

            auctionItems.add(auctionCardsContainer);
        }
    }


}
