package dashboard;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController  implements Initializable {

    @FXML
    private BarChart<String,Number> chart1;

    @FXML
    private BarChart<String,Number> chart2;

    @FXML
    private BarChart<String,Number> chart3;

    @FXML
    private BarChart<String,Number> chart4;
    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        populateCharts();
    }

    private void populateCharts()
    {

    }
}
