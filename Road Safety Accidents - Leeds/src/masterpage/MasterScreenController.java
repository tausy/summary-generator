package masterpage;

import java.net.URL;
import java.util.ResourceBundle;

import dao.MapDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import landingpage.LoadApp;
import login.LoginManager;
import map.Browser;

/**
 * This is the main master page in which all the screens are rendered
 */
public class MasterScreenController implements Initializable  {
	@FXML
	private Button openMap;

	@FXML
	private Menu  mainMenu;

	@Override // This method is called by the FXMLLoader when initialization is complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		mainMenu.setGraphic(new ImageView("/masterpage/stackedlines.png"));
	}




	@FXML
	public void openMapScreen() {
		loadMap();
	}
	@FXML
	public void logout() {
		LoadApp.stage.setHeight(250.0);
		LoadApp.stage.setWidth(360.0);
		LoadApp.stage.setResizable(false);
		LoadApp.stage.centerOnScreen();

		new LoginManager(LoadApp.scene).logout();
	}

	/**
	 * Load predict page
	 */

	@FXML
	public void loadPredictScreen()
	{
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/predict/predict.fxml"));
			AnchorPane anchorPane = (AnchorPane) loader.load();
			BorderPane borderPane = (BorderPane) LoadApp.scene.lookup("#borderPane");

			borderPane.setCenter(anchorPane);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads compare page
	 */

	@FXML
	public void loadCompare() {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/compare/compare.fxml"));
			AnchorPane anchorPane = (AnchorPane) loader.load();
			BorderPane borderPane = (BorderPane) LoadApp.scene.lookup("#borderPane");

			borderPane.setCenter(anchorPane);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
/**
 * Load Dashboard 
 */
	@FXML
	public void loadDashboard() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard/dashboard.fxml"));
			AnchorPane anchorPane = (AnchorPane) loader.load();
			BorderPane borderPane = (BorderPane) LoadApp.scene.lookup("#borderPane");

			borderPane.setCenter(anchorPane);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadMap() {
		try {
			LoginManager.browser = new Browser(MapDao.getAllLocations());
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/map.fxml"));
			AnchorPane anchorPane = (AnchorPane) loader.load();
			VBox vBox = (VBox) anchorPane.lookup("#mapBox");
			vBox.getChildren().addAll(LoginManager.browser);
			BorderPane borderPane = (BorderPane) LoadApp.scene.lookup("#borderPane");

			borderPane.setCenter(anchorPane);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
