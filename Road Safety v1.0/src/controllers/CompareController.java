package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import dao.ConnectionDAO;
import dao.FxDialogs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CompareController implements Initializable {

	/**
	 * Class variables for widgets
	 */
	@FXML
	private ScatterChart<String, Number> chart1;

	@FXML
	private ScatterChart<String, Number> chart2;

	@FXML
	private ComboBox<String> year1;
	@FXML
	private ComboBox<String> year2;

	@FXML
	private Menu mainMenu;

	@FXML
	private MenuBar menuBar;

	@FXML
	private Button apply;

	@FXML
	private MenuItem predict;

	@FXML
	private MenuItem dashboard;

	@FXML
	private MenuItem map;

	/**
	 * Method to load the FXML using FXMLLoader when initialization is complete
	 */
	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		mainMenu.setGraphic(new ImageView("/Images/stackedlines.png"));
		populateComboBoxes();
		chart1.setVisible(false);
		chart2.setVisible(false);
	}

	@FXML
	public void apply(ActionEvent ev) {
		// Get values from UI widgets
		String fromYear = year1 == null ? "2010" : year1.getValue();
		String toYear = year2 == null ? "2010" : year2.getValue();
		populateChart(chart1, fromYear);
		populateChart(chart2, toYear);
	}

	/**
	 * Method to populate combo boxes
	 */
	private void populateComboBoxes() {

		ArrayList<String> years = ConnectionDAO.yearsAvailable();
		// populate year combo boxes
		year1.getItems().addAll(years);
		year2.getItems().addAll(years);
	}

	/**
	 * Method called on click of Compare button
	 */
	private void populateChart(ScatterChart<String, Number> chart, String year) {

		// TODO query db
		XYChart.Series<String, Number> carAccidents, busAccidents, taxiAccidents, cycleAccidents = null;
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		chart.getData().clear();

		// Series of data to compare
		carAccidents = new XYChart.Series<String, Number>();
		busAccidents = new XYChart.Series<String, Number>();
		taxiAccidents = new XYChart.Series<String, Number>();
		cycleAccidents = new XYChart.Series<String, Number>();

		carAccidents.setName("Car Accidents");
		busAccidents.setName("Bus Accidents");
		taxiAccidents.setName("Taxi Accidents");
		cycleAccidents.setName("Cycle Accidents");
		xAxis.setLabel("Month-Year");
		yAxis.setLabel("#Casualties");

		// add car accidents data to carAccidents series
		LinkedHashMap<String, Integer> carData = ConnectionDAO.getVehicleAccidentsData(year, "Car");
		Set<String> keys = carData.keySet();
		for (String key : keys)
			carAccidents.getData().add(new XYChart.Data<String, Number>(key, carData.get(key)));

		// add bus accidents data to busAccidents series
		LinkedHashMap<String, Integer> busData = ConnectionDAO.getVehicleAccidentsData(year, "Bus");
		keys = busData.keySet();
		for (String key : keys)
			busAccidents.getData().add(new XYChart.Data<String, Number>(key, busData.get(key)));

		// add taxi accidents data to taxiAccidents series
		LinkedHashMap<String, Integer> taxiData = ConnectionDAO.getVehicleAccidentsData(year, "Taxi");
		keys = taxiData.keySet();
		for (String key : keys)
			carAccidents.getData().add(new XYChart.Data<String, Number>(key, taxiData.get(key)));

		// add cycle accidents data to cycleAccidents series
		LinkedHashMap<String, Integer> cycleData = ConnectionDAO.getVehicleAccidentsData(year, " Cycle");
		keys = cycleData.keySet();
		for (String key : keys)
			cycleAccidents.getData().add(new XYChart.Data<String, Number>(key, cycleData.get(key)));

		// Add all series to graph
		chart.getData().add(carAccidents);
		chart.getData().add(busAccidents);
		chart.getData().add(taxiAccidents);
		chart.getData().add(cycleAccidents);
		chart.setTitle("Accidents " + year);
		chart.setVisible(true);
		// chart.setAnimated(false);

	}

	@FXML
	public void loadPredict(ActionEvent ev) {
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
	public void loadDashboard(ActionEvent ev) {
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
	public void loadMap(ActionEvent ev) {
		// Hide this current window
		menuBar.getScene().getWindow().hide();

		Stage map = new Stage();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/Map.fxml"));
			Scene scene = new Scene(root);
			map.setScene(scene);
			map.show();
			map.setResizable(false);
			map.centerOnScreen();

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
