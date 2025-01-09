import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class ControllerUser {
    private BeanUser loggedInUser;

    private Main mainApplication;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label incorrect;

    @FXML
    private UserDao userDao;



    public ControllerUser() {
        userDao = new UserDao(new DatabaseHandler());
    }

    @FXML
    private void login() throws SQLException, InterruptedException {
        String username = usernameField.getText();
        String password = passwordField.getText();


        BeanUser user = userDao.getUserByUsername(username);

        if (user == null) {
            incorrect.setText("Invalid username or password");
        } else if (!user.getPassword().equals(password)) {
            incorrect.setText("Invalid username or password");
        } else {
            incorrect.setText("correct");
            mainApplication.showAuctionsPage(user);
        }
    }

    @FXML
    void registerButtonHandle(ActionEvent event) throws IOException {
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Register.fxml")));
            if (root != null) {
                Scene scene = new Scene(root);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage.setScene(scene);
                stage.show();


                ((Stage) usernameField.getScene().getWindow()).close();
            } else {
                Exception exception = new Exception();
                exception.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setMainApplication(Main mainApplication) {
        this.mainApplication = mainApplication;
    }


    public void setLoggedInUser(BeanUser user) {
        this.loggedInUser = user;
    }
}