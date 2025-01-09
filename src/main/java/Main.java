import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/***
 * 
 * @author ADMIN
 * main landing module for the different application components. 
 * extends Application class from javafx package
 * 
 */
public class Main extends Application {
    private Stage primaryStage;
    private ControllerUser userController;
    private ControllerAuctionsPage auctionsPageController;
    private BeanUser loggedInUser;

    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * 
     * @param primaryStage of typeStage specifies the window properties and instantiates display
     */

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showLogin();
    }

    /**
     * Specify resource responsible for display. 
     * attempt rendering of page, and if fails log an exception
     */
    public void showLogin() {
        String loginFormResource = "login.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(loginFormResource));
            Parent root = loader.load();
            userController = loader.getController();
            userController.setMainApplication(this);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * 
     * @param loggedInUser 
     * 
     */
    public void showAuctionsPage(BeanUser loggedInUser) {
        this.loggedInUser = loggedInUser;
        String auctionsPageResource = "auctions_page.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(auctionsPageResource));
            Parent root = loader.load();
            auctionsPageController = loader.getController();
            auctionsPageController.setMainApplication(this);
            auctionsPageController.setLoggedInUser(loggedInUser);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }



    public void setScene(Parent root) {
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}