package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.controlsfx.control.textfield.TextFields;

import dao.Browser;
import dao.ConnectionDAO;
import dao.FxDialogs;
import dao.LocationVO;
import dao.MapDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MapController implements Initializable {

	@FXML
	private Button apply;

	@FXML
	private Button newAccident;

	@FXML
	private Button reset;

	@FXML
	private TextField searchMapText;

	@FXML
	private CheckBox pedCheckBox;

	@FXML
	private CheckBox cycleChkBox;

	@FXML
	private CheckBox motorcycleChkBox;

	@FXML
	private CheckBox carChkBox;

	@FXML
	private CheckBox vanChkBox;

	@FXML
	private CheckBox busChkBox;

	@FXML
	private CheckBox lorryChkBox;

	@FXML
	private CheckBox otherChkBox;

	@FXML
	private ComboBox<String> speedCombo;

	@FXML
	private ComboBox<String> yearCombo;

	@FXML
	private ComboBox<String> ageCombo;

	@FXML
	private ComboBox<String> sexCombo;

	@FXML
	private ComboBox<String> weatherCombo;

	@FXML
	private VBox map;

	private Browser webview;

	@FXML
	private MenuItem compare;

	@FXML
	private MenuItem dashboard;

	@FXML
	private MenuItem predict;

	@FXML
	private Menu mainMenu;

	@FXML
	private MenuBar menuBar;

	@Override // This method is called by the FXMLLoader when initialization is complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

		// set menu bar icon
		mainMenu.setGraphic(new ImageView("/Images/stackedlines.png"));

		// add map's web view to vbox
		webview = new Browser(MapDao.getAllLocations());
		map.getChildren().add(webview);

		// populate combo boxes
		populateComboBoxes();

		List<String> list = ConnectionDAO.getPostcodes();

		TextFields.bindAutoCompletion(searchMapText, t -> {
			return list.stream().filter(elem -> {
				return elem.toLowerCase().startsWith(t.getUserText().toLowerCase());
			}).collect(Collectors.toList());
		});

	}

	private void populateComboBoxes() {

		// populate sex combo box
		ObservableList<String> options = FXCollections.observableArrayList("All", "Male", "Female", "Other");
		sexCombo.getItems().addAll(options);

		// populate age combo box
		ArrayList<String> ages = ConnectionDAO.agesAvailable();
		ageCombo.getItems().add("All");
		ageCombo.getItems().addAll(ages);

		// populate year combo box
		ArrayList<String> years = ConnectionDAO.yearsAvailable();
		yearCombo.getItems().add("All");
		yearCombo.getItems().addAll(years);

		// populate speed combo box
		speedCombo.getItems().add("All");
		ArrayList<String> speeds = ConnectionDAO.speedsAvailable();
		speedCombo.getItems().addAll(speeds);

		// populate weather combo box
		weatherCombo.getItems().add("All");
		ArrayList<String> weathers = ConnectionDAO.weathersAvailable();
		weatherCombo.getItems().addAll(weathers);

		// search field
		searchMapText.setPromptText("Search areas..");
		searchMapText.setFocusTraversable(false);
	}

	@FXML
	private void resetFilters() {
		
	}

	@FXML
	private void applyFilters() {

		ArrayList<LocationVO> locationList = new ArrayList<LocationVO>();

		// Parameters from UI
		String pedestrian = pedCheckBox.isSelected() ? "Pedestrian" : "";
		String cycle = cycleChkBox.isSelected() ? "Cycle" : "";
		String motorcycle = motorcycleChkBox.isSelected() ? "Motorcycle" : "";
		String car = carChkBox.isSelected() ? "Car" : "";
		String van = vanChkBox.isSelected() ? "Van" : "";
		String bus = busChkBox.isSelected() ? "Bus" : "";
		String lorry = lorryChkBox.isSelected() ? "Lorry" : "";
		String other = otherChkBox.isSelected() ? "Other" : "";
		String speed = speedCombo.getValue();
		String year = yearCombo.getValue();
		String age = ageCombo.getValue();
		String sex = sexCombo.getValue();
		String weather = weatherCombo.getValue();
		String search = searchMapText.getText();

		// Base query
		String baseQuery = null;

		ArrayList<String> latLong = null;
		if (!search.equals("")) {
			latLong = ConnectionDAO.getLatLongForPostcode(searchMapText.getText().trim());
		}

		if (pedestrian.equals("") && cycle.equals("") && motorcycle.equals("") && car.equals("") && van.equals("")
				&& bus.equals("") && lorry.equals("") && other.equals("") && speed == null && year == null
				&& age == null && sex == null && weather == null) {
			baseQuery = "Select easting, northing from accidentinfo where year = (select max(year) from accidentinfo)";
		} else {
			baseQuery = "Select easting, northing from accidentinfo where";

			if (!pedestrian.equals("")) {
				baseQuery = baseQuery + " lower(CasualtyClass) = 'pedestrian'";
			}

			if (!cycle.equals("")) {
				baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
						? baseQuery + " lower(TypeOfVehicle) like '% cycle%'"
						: baseQuery + " OR " + "lower(TypeOfVehicle) like '% cycle%'";
			}

			if (!motorcycle.equals("")) {
				baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
						? baseQuery + " lower(TypeOfVehicle) like '%motorcycle%'"
						: baseQuery + " OR lower(TypeOfVehicle) like '%motorcycle%'";
			}

			if (!car.equals("")) {
				baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
						? baseQuery + " lower(TypeOfVehicle) like '%car%'"
						: baseQuery + " OR lower(TypeOfVehicle) like '%car%'";
			}

			if (!van.equals("")) {
				baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
						? baseQuery + " lower(TypeOfVehicle) like '%van%'"
						: baseQuery + " OR lower(TypeOfVehicle) like '%van%'";
			}

			if (!bus.equals("")) {
				baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
						? baseQuery + " lower(TypeOfVehicle) like '%bus%'"
						: baseQuery + " OR lower(TypeOfVehicle) like '%bus%'";
			}

			if (!lorry.equals("")) {
				baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
						? baseQuery + " lower(TypeOfVehicle) like '%lorry%'"
						: baseQuery + " OR lower(TypeOfVehicle) like '%lorry%'";
			}

			if (!other.equals("")) {
				baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
						? baseQuery + " lower(TypeOfVehicle) like '%other%'"
						: baseQuery + " OR lower(TypeOfVehicle) like '%other%'";
			}

			if (speed != null) {
				if (!speed.equals("All")) {
					baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
							? baseQuery + " speed = '" + speed + "'"
							: baseQuery + " AND speed = '" + speed + "'";
				} else {
					baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
							? baseQuery + " speed like '%'"
							: baseQuery + " AND speed like '%'";
				}
			}

			if (year != null) {
				if (!year.equals("All")) {
					baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
							? baseQuery + " year = '" + year + "'"
							: baseQuery + " AND year = '" + year + "'";
				} else {
					baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
							? baseQuery + " year like '%'"
							: baseQuery + " AND year like '%'";
				}
			}

			if (age != null) {
				if (!age.equals("All")) {
					baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
							? baseQuery + " ageOfCasualty = '" + age + "'"
							: baseQuery + " AND ageOfCasualty = '" + age + "'";
				} else {
					baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
							? baseQuery + " ageOfCasualty like '%'"
							: baseQuery + " AND ageOfCasualty like '%'";
				}
			}

			if (sex != null) {
				if (!sex.equals("All")) {
					baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
							? baseQuery + " sexOfCasualty = '" + sex + "'"
							: baseQuery + " AND sexOfCasualty = '" + sex + "'";
				} else {
					baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
							? baseQuery + " sexOfCasualty like '%'"
							: baseQuery + " AND sexOfCasualty like '%'";
				}
			}

			if (weather != null) {
				if (!weather.equals("All")) {
					baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
							? baseQuery + " weatherConditions ='" + weather + "'"
							: baseQuery + " AND weatherConditions ='" + weather + "'";
				} else {
					baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
							? baseQuery + " weatherConditions like '%'"
							: baseQuery + " AND weatherConditions like '%'";
				}
			}

		}

			ResultSet resultSet = MapDao.getAllColumns(baseQuery);

			if (resultSet != null) {
				try {

					while (resultSet.next()) {

						locationList.add(MapDao.getLocationVo(resultSet));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}	

		webview.getWebView().getEngine().executeScript("clearMarkers()");
		webview.getWebView().getEngine().executeScript("initMap()");
		if (latLong != null) {
			webview.getWebView().getEngine().executeScript(
					"changeCenter(new google.maps.LatLng(" + latLong.get(0) + "," + latLong.get(1) + "))");
			webview.getWebView().getEngine().executeScript("changeZoom(15)");
		}
		
		
		String script = "drawMap(" + MapDao.getLocations(locationList) + ")";
		webview.getWebView().getEngine().executeScript(script);

	}

	@FXML
	private void loadCompare(ActionEvent ev) {
		// Hide this current window
		menuBar.getScene().getWindow().hide();

		Stage compare = new Stage();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/Compare.fxml"));
			Scene scene = new Scene(root);
			compare.setScene(scene);
			compare.show();
			compare.setResizable(false);
			compare.centerOnScreen();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	private void loadPredict(ActionEvent ev) {
		// Hide this current window
		menuBar.getScene().getWindow().hide();

		Stage predict = new Stage();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/Predict.fxml"));
			Scene scene = new Scene(root);
			predict.setScene(scene);
			predict.show();
			predict.setResizable(false);
			predict.centerOnScreen();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	private void loadDashboard(ActionEvent ev) {
		// Hide this current window
		menuBar.getScene().getWindow().hide();

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
	}

	@FXML
	public void loadScatter(ActionEvent ev) {
		// Hide this current window
		menuBar.getScene().getWindow().hide();

		Stage scatter = new Stage();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/Scatter.fxml"));
			Scene scene = new Scene(root);
			scatter.setScene(scene);
			scatter.show();
			scatter.setResizable(false);
			scatter.centerOnScreen();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	private void loadQuestions(ActionEvent ev) {
		// Hide this current window
		menuBar.getScene().getWindow().hide();

		Stage questions = new Stage();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/Questions.fxml"));
			Scene scene = new Scene(root);
			questions.setScene(scene);
			questions.show();
			questions.setResizable(false);
			questions.centerOnScreen();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	private void loadFacts(ActionEvent ev) {
		// Hide this current window
		menuBar.getScene().getWindow().hide();

		Stage facts = new Stage();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/Facts.fxml"));
			Scene scene = new Scene(root);
			facts.setScene(scene);
			facts.show();
			facts.setResizable(false);
			facts.centerOnScreen();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	private void logout(ActionEvent ev) {

		Optional<ButtonType> response = FxDialogs.showConfirm("Road Accidents Leeds",
				"Would you like to Logout the application?");
		if (response.isPresent() && response.get().getText().equals("OK")) {
			// Hide this current window
			menuBar.getScene().getWindow().hide();

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
		} else {
			ev.consume();
		}
	}
}
