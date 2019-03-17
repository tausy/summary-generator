package login;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.ConnectionDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import validation.RequiredField;

/** Controls the login screen */
public class LoginController {
  @FXML private TextField user;
  @FXML private TextField password;
  @FXML private Button loginButton;
  @FXML private Button guest;
  @FXML private RequiredField rqdUsername;
  @FXML private RequiredField rqdLoginPass;
  public void initialize() {}



  public void initManager(final login.LoginManager loginManager) {


    guest.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
          loginManager.showGuest();

      }
    });
    loginButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
      if(validateLoginInput())
      {
        String sessionID = authorize();
        if ( sessionID != null) {
        loginManager.authenticated(sessionID);
        }
      }
      }
    });
  }

  /**
   * Check authorization credentials.
   * 
   * If accepted, return a sessionID for the authorized session
   * otherwise, return null.
   */   
  private String authorize() {
    try
    {
      PreparedStatement statement =
              ConnectionDAO.getConnection().prepareStatement("select userName from accident.person where userName = ? and password= ?");
      statement.setString(1, user.getText());
      statement.setString(2,password.getText());
      ResultSet resultSet = statement.executeQuery();
      if(resultSet.next()==false){
        user.setText("");
        validateLoginInput();

      }
      else {
        do{
          String name=  resultSet.getString("userName");
          if(name.equals(user.getText()))
          {
            return generateSessionID();
          }

        }while (resultSet.next());
      }
      ConnectionDAO.getConnection().close();
    }catch (Exception e)
    {
      e.printStackTrace();
    }

    return null;

  }

  private boolean validateLoginInput()
  {
    boolean allGood=false;
    rqdUsername.eval();
    rqdLoginPass.eval();
    if (!(rqdUsername.hasErrorsProperty().get()||rqdLoginPass.hasErrorsProperty().get()))
    {
      allGood=true;
    }
    else {
      rqdUsername.eval();
    }

    return allGood;
  }
  private static int sessionID = 0;

  private String generateSessionID() {
    sessionID++;
    return "xyzzy - session " + sessionID;
  }
}
