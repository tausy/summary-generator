package compare;

import dao.MapDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class CompareController implements Initializable {

    @FXML
    private ScatterChart<CategoryAxis,Number> scatterChart;
    @FXML
    private ComboBox<String> weatherBox;
    @FXML
    private ComboBox<String> monthBox1;
    @FXML
    private ComboBox<String> monthBox2;
    @FXML
    private ComboBox<String> yearBox1;
    @FXML
    private ComboBox<String> yearBox2;

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        populateComboBoxes();
    }
    private void populateComboBoxes() {
        try {
            monthBox1.getItems().addAll("01","02","03","04","05","06","07","08","09","10","11","12");

            monthBox2.getItems().addAll("01","02","03","04","05","06","07","08","09","10","11","12");
            ResultSet rs = null;

            rs = MapDao.getAllColumns("Select distinct weatherConditions from accidentinfo");
            while (rs.next())
                weatherBox.getItems().add(rs.getString(1));
            weatherBox.getItems().add("All");
        }catch (Exception e)
        {

        }
    }
    @FXML
    public void compareAccidents()
    {
        //TODO query db
       // yearBox1.getValue();
        XYChart.Series series1 = new XYChart.Series();

        series1.setName("Option 1");
        series1.getData().add(new XYChart.Data("March 2009", 193.2));
        XYChart.Series series2 = new XYChart.Series();


        series2.getData().add(new XYChart.Data("March 2008", 33.6));
        scatterChart.getData().addAll(series1);
        scatterChart.getData().addAll(series2);

    }
}
