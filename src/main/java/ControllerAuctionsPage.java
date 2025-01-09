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

    /**
     * Sets the logged-in user.
     * 
     * @param user The logged-in user.
     */
    public void setLoggedInUser(BeanUser user) {
        this.loggedInUser = user;
    }

    /**
     * Sets the main application instance.
     * 
     * @param mainApplication The main application instance.
     */
    public void setMainApplication(Main mainApplication) {
        this.mainApplication = mainApplication;
    }

    /**
     * Initializes the controller class.
     * 
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        auctionManager = new AuctionManager();

        ArrayList<BeanAuction> auctions = auctionManager.getActiveAuctions();

        displayAuctions(auctions);
    }

    /**
     * Displays the given auctions.
     * 
     * @param auctions The list of auctions to be displayed.
     */
    private void displayAuctions(ArrayList<BeanAuction> auctions) {
        auctionCardsContainer.getChildren().clear();

        for (BeanAuction auction : auctions) {
            createAuctionCard(auction);
        }
    }

    /**
     * Creates an auction card for the given auction.
     * 
     * @param auction The auction for which to create a card.
     */
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

    /**
     * Handles the quit action.
     */
    @FXML
    private void quit() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the search action.
     */
    @FXML
    private void handleSearch() {
        String searchQuery = searchField.getText();

        ArrayList<BeanAuction> searchResults = auctionManager.searchAuctions(searchQuery);

        displayAuctions(searchResults);
    }

    /**
     * Handles the create auction action.
     */
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
