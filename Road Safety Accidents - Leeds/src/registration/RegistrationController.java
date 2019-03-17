package registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import dao.ConnectionDAO;
import dao.MapDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import landingpage.LoadApp;
import login.LoginManager;
import map.Browser;
import validation.RequiredField;

public class RegistrationController {

    @FXML
    private TextField firstName;
    @FXML
    private TextField surName;
    @FXML
    private TextField gender;
    @FXML
    private TextField contact;
    @FXML
    private TextField address;
    @FXML
    private TextField userName;
    @FXML
    private TextField postalCode;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private RequiredField rqdFirstName;
    @FXML
    private RequiredField rqdSurName;
    @FXML
    private RequiredField rqdGender;
    @FXML
    private RequiredField rqdContact;
    @FXML
    private RequiredField rqdAddress;
    @FXML
    private RequiredField rqdUserName;
    @FXML
    private RequiredField rqdPassword;
    @FXML
    private RequiredField rqdConfirmPassword;


    @FXML
    public void registerPage()
    {
        try{
            if(validateAllInputs())
            {
                Connection conn=ConnectionDAO.getConnection();
                String query = " insert into person (firstName, surName, gender, contact, address,userName,postalCode,email,password,confirmPassword)"
                        + " values (?, ?, ?, ?, ?,?,?,?,?,?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString (1, firstName.getText());
                preparedStmt.setString (2, surName.getText());
                preparedStmt.setString (3, gender.getText());
                preparedStmt.setString (4, contact.getText());
                preparedStmt.setString (5, address.getText());
                preparedStmt.setString (6, userName.getText());
                preparedStmt.setString (7, postalCode.getText());
                preparedStmt.setString (8, email.getText());
                preparedStmt.setString (9, password.getText());
                preparedStmt.setString (10, confirmPassword.getText());

                // execute the preparedstatement
                //preparedStmt.execute();
                if(preparedStmt.executeUpdate()>0)
                {
                    loadMap();
                }
                conn.close();

            }


        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void loadMap() throws IOException
    {
        LoadApp.stage.setHeight(700.0);
        LoadApp.stage.setWidth(700.0);

        LoginManager.browser = new Browser(MapDao.getAllLocations());
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/resources/map.fxml"));
        AnchorPane anchorPane=(AnchorPane)loader.load();
        VBox vBox=(VBox) anchorPane.lookup("#mapBox");

        //  vBox.setPadding(new Insets(3));
        vBox.getChildren().addAll(LoginManager.browser);

        // scene = new Scene(anchorPane, 750, 600, Color.web("#666970"));
        LoadApp.scene.setRoot(anchorPane);
    }

    private boolean validateAllInputs()
    {
        boolean allGood=false;
        rqdFirstName.eval();
        rqdSurName.eval();
        rqdGender.eval();
        rqdContact.eval();
        rqdAddress.eval();
        rqdUserName.eval();
        rqdPassword.eval();
        rqdConfirmPassword.eval();

        if(!confirmPassword.getText().equals(password.getText()))
        {
            System.out.println("not equal");
            confirmPassword.setText("");
            rqdConfirmPassword.eval();
            allGood=false;
        }
        if (!(rqdFirstName.hasErrorsProperty().get() || rqdSurName.hasErrorsProperty().get() || rqdGender.hasErrorsProperty().get()
        || rqdContact.hasErrorsProperty().get() || rqdAddress.hasErrorsProperty().get() || rqdUserName.hasErrorsProperty().get()
        || rqdPassword.hasErrorsProperty().get() || rqdConfirmPassword.hasErrorsProperty().get()))
        {
            System.out.println("error not present");
            allGood=true;
        }
        return allGood;
    }
}
