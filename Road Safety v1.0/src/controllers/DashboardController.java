package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import dao.ConnectionDAO;
import dao.Constants;
import dao.FxDialogs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class to control Dashboard screen widgets and activities
 * 
 * @author
 *
 */
public class DashboardController implements Initializable {

	@FXML
	private Menu mainMenu;

	@FXML
	private MenuBar menuBar;

	@FXML
	private Label lblWelcome;

	@FXML
	private PieChart pie1;

	@FXML
	private PieChart pie2;

	@FXML
	private PieChart pie3;

	@FXML
	private BarChart<String, Number> bar1;

	@FXML
	private Label lblPie1;
	
	@FXML
	private Label area1;
	
	@FXML 
	private Label area2;
	
	@FXML
	private BarChart<String, Number> bar2;
	
	@FXML
	private MenuItem compare;
	
	@FXML
	private MenuItem map;
	
	@FXML
	private MenuItem predict;
	

	/**
	 * This method is called by the FXMLLoader when initialization is complete
	 */
	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		mainMenu.setGraphic(new ImageView("/Images/stackedlines.png"));
		area1.setText(Constants.DASHBOARD_TEXT_BOX1);
		area2.setText(Constants.DASHBOARD_TEXT_BOX2);
		area1.setFocusTraversable(false);
		area2.setFocusTraversable(false);
		populateCharts();
	}

	/**
	 * Method to populate charts
	 */
	private void populateCharts() {
		populatePieChart1();
		populatePieChart2();
		populateBarChart1();
		populateBarChart2();
	}

	private void populatePieChart1() {
		Hashtable<String, Integer> data = ConnectionDAO.getDataForChart1();

		PieChart.Data slice1 = new PieChart.Data("Bus", data.get("Bus"));
		PieChart.Data slice2 = new PieChart.Data("Car", data.get("Car"));
		PieChart.Data slice3 = new PieChart.Data("Cycle", data.get("Cycle"));
		PieChart.Data slice4 = new PieChart.Data("Motorcycle", data.get("Motorcycle"));
		PieChart.Data slice5 = new PieChart.Data("Other", data.get("Other"));

		pie1.getData().addAll(slice1, slice2, slice3, slice4, slice5);
		pie1.setLabelsVisible(true);
		pie1.setLegendVisible(false);
		pie1.setTitle("Vehicle");

		if (pie1.getLabelsVisible()) {
			pie1.getData().forEach(d -> {
				Optional<Node> opTextNode = pie1.lookupAll(".chart-pie-label").stream()
						.filter(n -> n instanceof Text && ((Text) n).getText().contains(d.getName())).findAny();
				if (opTextNode.isPresent()) {
					((Text) opTextNode.get()).setText(d.getName() + " - " + (int) d.getPieValue() + "%");
				}
			});
		}
		pie1.setStartAngle(10);
		pie1.setAnimated(true);
	}

	private void populatePieChart2() {
		Hashtable<String, Integer> data = ConnectionDAO.getDataForChart2();

		PieChart.Data slice1 = new PieChart.Data("Child", data.get("Child"));
		PieChart.Data slice2 = new PieChart.Data("Teenager", data.get("Teenager"));
		PieChart.Data slice3 = new PieChart.Data("Young-Adult", data.get("Young-Adult"));
		PieChart.Data slice4 = new PieChart.Data("Adult", data.get("Adult"));
		PieChart.Data slice5 = new PieChart.Data("Middle-Aged", data.get("Middle-Aged"));
		PieChart.Data slice6 = new PieChart.Data("Senior", data.get("Senior"));

		pie2.getData().addAll(slice1, slice2, slice3, slice4, slice5, slice6);
		pie2.setLabelsVisible(true);
		pie2.setLegendVisible(false);
		pie2.setTitle("Casualty");
		if (pie2.getLabelsVisible()) {
			pie2.getData().forEach(d -> {
				Optional<Node> opTextNode = pie2.lookupAll(".chart-pie-label").stream()
						.filter(n -> n instanceof Text && ((Text) n).getText().contains(d.getName())).findAny();
				if (opTextNode.isPresent()) {
					((Text) opTextNode.get()).setText(d.getName() + " - " + (int) d.getPieValue() + "%");
				}
			});
		}
		pie2.setAnimated(true);
		pie2.setStartAngle(20);
	}

	/**
	 * Populate chart 3
	 */
	private void populateBarChart1() {

		XYChart.Series<String, Number> totalCasualties = null;
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		bar1.setTitle("Total Casualties");
		xAxis.setLabel("Year");
		yAxis.setLabel("Casualty");

		totalCasualties = new XYChart.Series<String, Number>();
		totalCasualties.setName("Total Casualties");
		LinkedHashMap<String, Integer> data = ConnectionDAO.getDataForChart3();

		// Populate total casualties
		Set<String> keys = data.keySet();
		for (String key : keys) {
			totalCasualties.getData().add(new XYChart.Data<String, Number>(key, data.get(key)));
		}

		// Add series to chart
		bar1.getData().add(totalCasualties);
	}

	
	/**
	 * Populate chart 3
	 */
	private void populateBarChart2() {

		XYChart.Series<String, Number> totalCasualtiesMales = null;
		XYChart.Series<String, Number> totalCasualtiesFemales = null;
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		bar2.setTitle("Total Casualties");
		xAxis.setLabel("Year");
		yAxis.setLabel("Casualty");

		totalCasualtiesMales = new XYChart.Series<String, Number>();
		totalCasualtiesFemales = new XYChart.Series<String, Number>();
		totalCasualtiesMales.setName("Male");
		totalCasualtiesFemales.setName("Female");
		
		ArrayList<LinkedHashMap<String, Integer>> data = ConnectionDAO.getDataForChart4();
		LinkedHashMap<String, Integer> males = data.get(0);
		LinkedHashMap<String, Integer> females = data.get(1);
		
		// Populate total casualties
		Set<String> keys = males.keySet();
		for (String key : keys) {
			totalCasualtiesMales.getData().add(new XYChart.Data<String, Number>(key, males.get(key)));
		}

		// Populate total casualties
				keys = males.keySet();
				for (String key : keys) {
					totalCasualtiesFemales.getData().add(new XYChart.Data<String, Number>(key, females.get(key)));
				}
		// Add series to char
		bar2.getData().add(totalCasualtiesMales);
		bar2.getData().add(totalCasualtiesFemales);
	}
	
	@FXML
	public void loadCompare(ActionEvent ev) {
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
