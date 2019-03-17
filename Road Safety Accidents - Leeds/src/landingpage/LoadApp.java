package landingpage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import login.LoginManager;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadApp extends Application {
    public static Scene scene;
    public static Stage stage;
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception {
         scene = new Scene(new StackPane());

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("Untitled.fxml")
            );
            scene.setRoot((Parent) loader.load());
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage.setScene(scene);

        stage.show();
        LoadApp.stage=stage;
    }
}
