package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.ConnectionDAO;
import dao.Constants;
import dao.FxDialogs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import weka.AccidentsForcaster;

public class PredictController implements Initializable {

    @FXML 
    private LineChart<String, Number> chart1;

    @FXML 
    private Label info;
    
    @FXML 
    private MenuBar menuBar;
    
    @FXML 
    private Menu mainMenu;
    
    @FXML 
    private MenuItem compare;
    
    @FXML 
    private MenuItem dashboard;
    
    @FXML
	private MenuItem map;
    
    

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    	mainMenu.setGraphic(new ImageView("/Images/stackedlines.png"));
    	populateCharts();
        info.setText(Constants.PREDICT_TEXT_BOX);
    }

    private void populateCharts(){
      //TODO populate charts
    	populateChart1();
    }
    
    private void populateChart1(){
    	
    	XYChart.Series<String, Number> accidents = null;
    	final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        chart1.setTitle("Predicted Casualties - Monthly");
        
        xAxis.setLabel("Month-Year");
        yAxis.setLabel("Number of Casualties");

        ArrayList<Integer> data = AccidentsForcaster.getMonthlyPredictions();
        System.out.print(data);
        
		accidents = new XYChart.Series<String, Number>();
		accidents.setName("#Casualties");
		if(!data.isEmpty()) {
			ArrayList<String> predSeries = ConnectionDAO.getCasualtiesForPredData(data.size());
			System.out.print(predSeries);
			for(int idx = 0; idx < predSeries.size(); idx++) {
				System.out.println(predSeries.get(idx)+","+data.get(idx));
			accidents.getData().add(new XYChart.Data<String, Number>(predSeries.get(idx), data.get(idx)));
			}
		}
		
		chart1.getData().add(accidents);
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
