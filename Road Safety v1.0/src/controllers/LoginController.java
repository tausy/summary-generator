package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import dao.ConnectionDAO;
import dao.FxDialogs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/** Controls the login screen */
public class LoginController implements Initializable {

	@FXML
	private TextField user;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Button btnLogin;

	@FXML
	private Button btnSignup;

	@FXML
	private Button btnGuest;

	@FXML
	private ImageView btnBack;
	
	PopOver popOver = new PopOver();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Label label = new Label("");
		// label.setPadding(new Insets(0, 10, 0, 10));
		label.setTextFill(Color.WHITE);
		HBox box = new HBox(0);
		box.setPadding(new Insets(5, 5, 5, 5));
		box.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
		box.getChildren().add(label);

		ValidationSupport validationSupport = new ValidationSupport();
		validationSupport.registerValidator(user, Validator.createEmptyValidator("Username is required"));
		validationSupport.registerValidator(passwordField, Validator.createEmptyValidator("Username is required"));
		
		user.setOnMouseEntered(mouseEvent -> {
			label.setText("Username is required!");
			popOver.setContentNode(box);
			// Show PopOver when mouse enters label
			popOver.show(user);
		});

		user.setOnMouseExited(mouseEvent -> {
			// Hide PopOver when mouse exits label
			popOver.hide();
		});
		
		passwordField.setOnMouseEntered(mouseEvent -> {
			label.setText("Password is required!");
			popOver.setContentNode(box);
			// Show PopOver when mouse enters label
			popOver.show(passwordField);
		});

		passwordField.setOnMouseExited(mouseEvent -> {
			// Hide PopOver when mouse exits label
			popOver.hide();
		});

	}

	@FXML
	public void login(ActionEvent ev) {
		String username = user.getText().trim();
		String password = passwordField.getText().trim();
		boolean success = ConnectionDAO.login(username, password);

		if (success) {
			btnLogin.getScene().getWindow().hide();

			Stage dashboard = new Stage();
			try {
				Parent root = FXMLLoader.load(getClass().getResource("/FXML/Dashboard.fxml"));
				Scene scene = new Scene(root);
				dashboard.setScene(scene);
				dashboard.show();
				dashboard.setResizable(false);
				dashboard.centerOnScreen();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			FxDialogs.showError("Road Accidents Leeds", "Username or Password is Incorrect");
			user.setText("");
			passwordField.setText("");
		}

	}

	@FXML
	public void signup(ActionEvent ev) {

		btnSignup.getScene().getWindow().hide();

		Stage signup = new Stage();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/Registration.fxml"));
			Scene scene = new Scene(root);
			signup.setScene(scene);
			signup.show();
			signup.setResizable(false);
			signup.centerOnScreen();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	public void back(MouseEvent ev) {

		if (ev.getEventType() == MouseEvent.MOUSE_CLICKED) {
			btnBack.getScene().getWindow().hide();

			Stage homepage = new Stage();
			try {
				Parent root = FXMLLoader.load(getClass().getResource("/FXML/Homepage.fxml"));
				Scene scene = new Scene(root);
				homepage.setScene(scene);
				homepage.show();
				homepage.setResizable(false);
				homepage.centerOnScreen();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	@FXML
	public void guest(ActionEvent ev) {

		btnGuest.getScene().getWindow().hide();

			Stage guestpage = new Stage();
			try {
				Parent root = FXMLLoader.load(getClass().getResource("/FXML/Guest.fxml"));
				Scene scene = new Scene(root);
				guestpage.setScene(scene);
				guestpage.show();
				guestpage.setResizable(false);
				guestpage.centerOnScreen();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

}
