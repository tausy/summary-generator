package login;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.MapDao;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import landingpage.LoadApp;
import map.Browser;

/** Manages control flow for logins */
public class LoginManager {
  private Scene scene;
  public static Browser browser;

  public LoginManager(Scene scene) {
    this.scene = scene;
  }

  /**
   * Callback method invoked to notify that a user has been authenticated.
   * Will show the main application screen.
   */
  public void authenticated(String sessionID) {
    showMap();
  }


  /**
   * Callback method invoked to notify that a user has logged out of the main application.
   * Will show the login application screen.
   */
  public void logout() {
    showLoginScreen();
  }

  public void showLoginScreen() {
    try {
      FXMLLoader loader = new FXMLLoader(
              getClass().getResource("login.fxml")
      );
      scene.setRoot((Parent) loader.load());
      login.LoginController controller =
              loader.<login.LoginController>getController();
      controller.initManager(this);
    } catch (IOException ex) {
      Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public void showMap() {
    try {
      LoadApp.stage.setHeight(700.0);
      LoadApp.stage.setWidth(700.0);

      browser = new Browser(MapDao.getAllLocations());
      FXMLLoader loader = new FXMLLoader(
              getClass().getResource("/resources/map.fxml"));
      AnchorPane anchorPane = (AnchorPane) loader.load();
      VBox vBox = (VBox) anchorPane.lookup("#mapBox");

      //  vBox.setPadding(new Insets(3));
      vBox.getChildren().addAll(browser);

      // scene = new Scene(anchorPane, 750, 600, Color.web("#666970"));
      scene.setRoot(anchorPane);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public void showGuest() {
    try {
      LoadApp.stage.setHeight(400.0);
      LoadApp.stage.setWidth(500.0);

      FXMLLoader loader = new FXMLLoader(
              getClass().getResource("/guestpage/guestpage.fxml")
      );
      scene.setRoot((Parent) loader.load());

    } catch (IOException ex) {
      Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
