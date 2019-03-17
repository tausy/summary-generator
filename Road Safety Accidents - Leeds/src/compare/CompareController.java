package compare;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.MapDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;

public class CompareController implements Initializable {

	@FXML
	private ScatterChart<String, Number> scatterChart;
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
			monthBox1.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");

			monthBox2.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
			ResultSet rs = null;

			// populate year combo box
			rs = MapDao.getAllColumns("Select distinct year from accidentinfo");
			while (rs.next())
				yearBox1.getItems().add(rs.getString(1));
			// populate year combo box
			rs = MapDao.getAllColumns("Select distinct year from accidentinfo");
			while (rs.next())
				yearBox2.getItems().add(rs.getString(1));
			rs = MapDao.getAllColumns("Select distinct weatherConditions from accidentinfo");
			while (rs.next())
				weatherBox.getItems().add(rs.getString(1));
			weatherBox.getItems().add("All");
		} catch (Exception e) {

		}
	}

	@FXML
	public void compareAccidents() {
		
		// TODO query db
		XYChart.Series<String, Number> accidentsSeries = null;
		scatterChart.getData().clear();
		String fromYear = yearBox1.getValue();
		String toYear = yearBox2.getValue();
		String fromMonth = monthBox1.getValue();
		String toMonth = monthBox2.getValue();
		String weather = weatherBox.getValue().equals("All") ? "%" : weatherBox.getValue();

		ResultSet rs = null;

		// populate sex combo box
		rs = MapDao.getAllColumns("select " + "month(accidentdate), year(accidentdate), count(referenceNumber) "
				+ "from accidentinfo " + "where weatherConditions like '" + weather + "' and year(accidentdate) >= '"
				+ fromYear + "' and year(accidentdate) <= '" + toYear + "' and month(accidentdate) >= '" + fromMonth
				+ "' and month(accidentdate) <= '" + toMonth + "' group by month(accidentdate), year(accidentdate)");

		accidentsSeries = new XYChart.Series<String, Number>();
		try {
			accidentsSeries.setName("Yearwise Accidents");
			while (rs.next()) {

				accidentsSeries.getData().add(new XYChart.Data<String, Number>(rs.getString(1) + "-" + rs.getString(2),
						Integer.parseInt(rs.getString(3))));
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		scatterChart.getData().add(accidentsSeries);
		scatterChart.setAnimated(false);

	}
}
