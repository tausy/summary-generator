package guestpage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import landingpage.LoadApp;
import masterpage.MasterScreenController;
import validation.RequiredField;

import java.net.URL;
import java.util.ResourceBundle;

public class GuestController  implements Initializable {

    @FXML private TextField userName;
    @FXML private TextField firstName;
    @FXML private TextField guestNumber;
    @FXML private TextField age;
    @FXML private TextField gender;

    @FXML private RequiredField rqdUsername;
    @FXML private RequiredField rqdFirstName;
    @FXML private RequiredField rqdAge;
    @FXML private RequiredField rqdGender;


    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        //TODO get from DB
        guestNumber.setText("Guest Number ");
    }

    @FXML
    public void openMasterPage()
    {
        if(validateLoginInput())
        {
            //TODO DB SAVE
            loadMap();
        }
    }

    private void loadMap()
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

    private boolean validateLoginInput()
    {
        boolean allGood=false;
        rqdUsername.eval();
        rqdFirstName.eval();
        rqdGender.eval();
        rqdAge.eval();
        if (!(rqdUsername.hasErrorsProperty().get()||rqdFirstName.hasErrorsProperty().get()||rqdAge.hasErrorsProperty().get()
        ||rqdGender.hasErrorsProperty().get()))
        {
            allGood=true;
        }
        else {
            rqdUsername.eval();
        }

        return allGood;
    }
}
