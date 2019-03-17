package landingpage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import login.LoginManager;

public class HomepageController {

    @FXML private Button exit;
    @FXML private Button signIn;
    @FXML private Button register;

    @FXML
    public void exit()
    {

    }

    @FXML
    public void login()
    {
        LoadApp.stage.setHeight(250.0);
        LoadApp.stage.setWidth(360.0);

        LoginManager loginManager = new LoginManager(LoadApp.scene);
        loginManager.showLoginScreen();

    }

    @FXML
    public void registerLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/registration/registration.fxml")
            );
            LoadApp.stage.setHeight(505.0);
            LoadApp.stage.setWidth(600.0);

            LoadApp.scene.setRoot((Parent) loader.load());

        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
