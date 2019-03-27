package predict;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.Constants;
import dao.MapDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import weka.AccidentsForcaster;

public class PredictController   implements Initializable {

    @FXML private LineChart<String, Number> lineChart1;

    @FXML private TextArea textArea;

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        populateCharts();
        textArea.setText(Constants.PREDICT_TEXT_BOX);
    }

    private void populateCharts()
    {
      //TODO populate charts
    	populateChart1();
    }
    
    private void populateChart1()
    {
    	ResultSet rs = null;
    	XYChart.Series<String, Number> accidents = null;
    	final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        lineChart1.setTitle("Predicted Casualties - Monthly");
        
        xAxis.setLabel("Month-Year");
        yAxis.setLabel("Number of Casualties");

        ArrayList<Integer> data = AccidentsForcaster.getMonthlyPredictions();
        
		accidents = new XYChart.Series<String, Number>();
		try {
			//
			rs = MapDao.getAllColumns("select month(accidentdate), year(accidentdate) from accidentinfo order by year(accidentdate) desc, month(accidentdate) desc limit 1");
			rs.next();
			int month = rs.getInt(1);
			int year = rs.getInt(2);
			accidents.setName("#Casualties");
			
			for(int i=0; i<12; i++) {
				if(month > 12) { month=01; year++;}
				accidents.getData().add(new XYChart.Data<String, Number>(month+"-"+year, data.get(i)));
				month++;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		lineChart1.getData().add(accidents);
		lineChart1.setAnimated(false);
    }

}
