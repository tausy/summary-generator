package registration;

import java.sql.Connection;
import java.sql.PreparedStatement;

import dao.ConnectionDAO;
import dao.Constants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import landingpage.LoadApp;
import masterpage.MasterScreenController;
import validation.RegexValidator;
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
    private RegexValidator rqdFirstName;
    @FXML
    private RegexValidator rqdSurName;
    @FXML
    private RegexValidator rqdGender;
    @FXML
    private RegexValidator rqdContact;
    @FXML
    private RequiredField rqdAddress;
    @FXML
    private RequiredField rqdUserName;
    @FXML
    private RegexValidator rqdEmail;
    @FXML
    private RegexValidator rqdPassword;
    @FXML
    private RegexValidator rqdConfirmPassword;


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
                    //showMap();
                }
                conn.close();

            }


        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void showMap() {
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
    private boolean validateAllInputs()
    {
    	boolean allGood=false;
    	RegexValidator regexValidator = new RegexValidator();
    	
    	regexValidator.setRegex(Constants.REGEX_NAME);
        rqdFirstName.eval();
        rqdSurName.eval();
        
        regexValidator.setRegex(Constants.REGEX_GENDER);
        rqdGender.eval();
        
        regexValidator.setRegex(Constants.REGEX_PHONE);
        rqdContact.eval();
        rqdAddress.eval();
        
        rqdUserName.eval();
        
        regexValidator.setRegex(Constants.REGEX_PASSWORD);
        rqdPassword.eval();
        rqdConfirmPassword.eval();

        regexValidator.setRegex(Constants.REGEX_EMAIL);
        rqdEmail.eval();
        
        if(!confirmPassword.getText().equals(password.getText()))
        {
            System.out.println("not equal");
            confirmPassword.setText("");
            rqdConfirmPassword.eval();
            allGood=false;
        }
        if (!(rqdFirstName.hasErrorsProperty().get() || rqdSurName.hasErrorsProperty().get() || rqdGender.hasErrorsProperty().get()
        || rqdContact.hasErrorsProperty().get() || rqdAddress.hasErrorsProperty().get() || rqdUserName.hasErrorsProperty().get()
        || rqdPassword.hasErrorsProperty().get() || rqdConfirmPassword.hasErrorsProperty().get() || rqdEmail.hasErrorsProperty().get()))
        {
            System.out.println("error not present");
            allGood=true;
        }
        return allGood;
    }
}
