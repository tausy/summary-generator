package guestpage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import landingpage.LoadApp;
import masterpage.MasterScreenController;

public class GuestController {

    @FXML
    public void openMasterPage()
    {
        try {
            LoadApp.stage.setHeight(700.0);
            LoadApp.stage.setWidth(700.0);
            LoadApp.stage.setResizable(false);
            LoadApp.stage.centerOnScreen();

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/masterpage/masterscreen.fxml"));
            AnchorPane anchorPane = (AnchorPane) loader.load();

            // scene = new Scene(anchorPane, 750, 600, Color.web("#666970"));
            LoadApp.scene.setRoot(anchorPane);
            new MasterScreenController().loadMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
