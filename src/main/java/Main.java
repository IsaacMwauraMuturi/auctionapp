import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;
    private ControllerUser userController;
    private ControllerAuctionsPage auctionsPageController;
    private BeanUser loggedInUser;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showLogin();
    }

    public void showLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            userController = loader.getController();
            userController.setMainApplication(this);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void showAuctionsPage(BeanUser loggedInUser) {
        this.loggedInUser = loggedInUser;
        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("auctions_page.fxml"));
            Parent root = loader.load();
            auctionsPageController = loader.getController();
            auctionsPageController.setMainApplication(this);
            auctionsPageController.setLoggedInUser(loggedInUser);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }



    public void setScene(Parent root) {
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}