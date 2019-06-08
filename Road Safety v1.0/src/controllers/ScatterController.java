package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import dao.ConnectionDAO;
import dao.FxDialogs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Class to control Scatter screen widgets and activities
 * 
 * @author
 *
 */
public class ScatterController implements Initializable {

	@FXML
	private ScatterChart<String, Number> chart1;

	@FXML
	private ComboBox<String> comboBox;
	
	@FXML 
	private Label lbl;
		
	@FXML
	private Menu mainMenu;

	@FXML
	private MenuBar menuBar;
	
	@FXML
	private MenuItem compare;
	
	@FXML
	private MenuItem map;
	
	@FXML
	private MenuItem predict;
	
	@FXML
	private MenuItem dashboard;
	

	/**
	 * This method is called by the FXMLLoader when initialization is complete
	 */
	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		mainMenu.setGraphic(new ImageView("/Images/stackedlines.png"));
		populateComboBox();
		
		comboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
			if(newValue.equals("roadSurface"))
				lbl.setText("The Chart depicts different road surface conditions and the corresponding "
						+ "number of accidents happened on that road surface. The X-axis contains different road surfaces while Y-axis shows number of "
						+ "accidents.");
			else if(newValue.equals("Weather"))
				lbl.setText("The Chart depicts different weather conditions and the corresponding "
						+ "number of accidents happened. The X-axis contains different weathers while Y-axis shows number of "
						+ "accidents.");
			else
				lbl.setText("The Chart depicts different lightening conditions and the corresponding "
						+ "number of accidents happened. The X-axis contains different lightening while Y-axis shows number of "
						+ "accidents.");
			refreshChart(newValue);
	    }
	    );
	}

	private void populateComboBox() {
		ObservableList<String> options = FXCollections.observableArrayList("Road Surface", "Weather", "Lightening");
		comboBox.getItems().addAll(options);
	}
	
	
	private void refreshChart(String col) {
		Hashtable<String, Integer> data = ConnectionDAO.getScatterData(col);

		XYChart.Series<String, Number> dataSeries = null;
		final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
		chart1.getData().clear();
		
		
		// Series of data to compare
		dataSeries = new XYChart.Series<String, Number>();
		
		//dataSeries.setName(col);
		xAxis.setLabel(col);
        yAxis.setLabel("Number of Casualties");
        
		
			// add data to data series
			Set<String> keys = data.keySet();
			for(String key : keys)
			dataSeries.getData().add(new XYChart.Data<String, Number>(key, data.get(key)));

		// Add all series to graph
		chart1.getData().add(dataSeries);
		chart1.setTitle(col);
		chart1.setLegendVisible(false);
		chart1.setVisible(true);
		chart1.setAnimated(false);

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
