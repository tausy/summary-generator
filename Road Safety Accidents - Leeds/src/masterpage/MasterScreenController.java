package masterpage;

import dao.MapDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import landingpage.LoadApp;
import login.LoginManager;
import map.Browser;

public class MasterScreenController {
	@FXML
	private Button openMap;

	@FXML
	public void openMapScreen() {
		loadMap();
	}

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
