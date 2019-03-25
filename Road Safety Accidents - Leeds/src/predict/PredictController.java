package predict;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;

import java.net.URL;
import java.util.ResourceBundle;

public class PredictController   implements Initializable {

    @FXML private LineChart lineChart1;

    @FXML private LineChart lineChart2;

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        populateCharts();
    }

    private void populateCharts()
    {
      //TODO populate charts
    }

}
