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
import javafx.scene.control.TextArea;

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

	@FXML
	private TextArea compareTxArea;

	@Override // This method is called by the FXMLLoader when initialization is complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		populateComboBoxes();
		compareTxArea.setText("todo set text");
	}

	private void populateComboBoxes() {
		try {
			// populate from_month combo box
			monthBox1.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");

			// populate to_month combo box
			monthBox2.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
			ResultSet rs = null;

			// populate from_year combo box
			rs = MapDao.getAllColumns("Select distinct year from accidentinfo");
			while (rs.next())
				yearBox1.getItems().add(rs.getString(1));
			
			// populate to_year combo box
			rs = MapDao.getAllColumns("Select distinct year from accidentinfo");
			while (rs.next())
				yearBox2.getItems().add(rs.getString(1));
			
			//Populate weather combo box
			weatherBox.getItems().add("All");
			rs = MapDao.getAllColumns("Select distinct weatherConditions from accidentinfo");
			while (rs.next())
				weatherBox.getItems().add(rs.getString(1));
			
		} catch (Exception e) {

		}
	}

	@FXML
	public void compareAccidents() {
		
		// TODO query db
		XYChart.Series<String, Number> carAccidents, busAccidents, taxiAccidents, cycleAccidents  = null;
		scatterChart.getData().clear();
		String fromYear = yearBox1.getValue();
		String toYear = yearBox2.getValue();
		String fromMonth = monthBox1.getValue();
		String toMonth = monthBox2.getValue();
		String weather = weatherBox.getValue().equals("All") ? "%" : weatherBox.getValue();

		
		carAccidents = new XYChart.Series<String, Number>();
		busAccidents = new XYChart.Series<String, Number>();
		taxiAccidents = new XYChart.Series<String, Number>();
		cycleAccidents = new XYChart.Series<String, Number>();
		ResultSet rs = null;
		
		try {

		// add car accidents data to carAccidents series
		rs = MapDao.getAllColumns("select " + "month(accidentdate), year(accidentdate), count(referenceNumber) "
				+ "from accidentinfo " + "where weatherConditions like '" + weather + "' and year(accidentdate) >= '"
				+ fromYear + "' and year(accidentdate) <= '" + toYear + "' and month(accidentdate) >= '" + fromMonth
				+ "' and month(accidentdate) <= '" + toMonth + "' and lower(typeOfVehicle) like '%car%' group by month(accidentdate), year(accidentdate)");
		
			carAccidents.setName("Car Accidents");
			while (rs.next()) {
				carAccidents.getData().add(new XYChart.Data<String, Number>(rs.getString(1) + "-" + rs.getString(2),
						Integer.parseInt(rs.getString(3))));
			}
			
			// add bus accidents data to busAccidents series
			rs = MapDao.getAllColumns("select " + "month(accidentdate), year(accidentdate), count(referenceNumber) "
					+ "from accidentinfo " + "where weatherConditions like '" + weather + "' and year(accidentdate) >= '"
					+ fromYear + "' and year(accidentdate) <= '" + toYear + "' and month(accidentdate) >= '" + fromMonth
					+ "' and month(accidentdate) <= '" + toMonth + "' and lower(typeOfVehicle) like '%bus%' group by month(accidentdate), year(accidentdate)");
			
			busAccidents.setName("Bus Accidents");
			while (rs.next()) {
				busAccidents.getData().add(new XYChart.Data<String, Number>(rs.getString(1) + "-" + rs.getString(2),
						Integer.parseInt(rs.getString(3))));
			}
			
			// add taxi accidents data to taxiAccidents series
						rs = MapDao.getAllColumns("select " + "month(accidentdate), year(accidentdate), count(referenceNumber) "
								+ "from accidentinfo " + "where weatherConditions like '" + weather + "' and year(accidentdate) >= '"
								+ fromYear + "' and year(accidentdate) <= '" + toYear + "' and month(accidentdate) >= '" + fromMonth
								+ "' and month(accidentdate) <= '" + toMonth + "' and lower(typeOfVehicle) like '%taxi%' group by month(accidentdate), year(accidentdate)");
						
						taxiAccidents.setName("Taxi Accidents");
						while (rs.next()) {
							taxiAccidents.getData().add(new XYChart.Data<String, Number>(rs.getString(1) + "-" + rs.getString(2),
									Integer.parseInt(rs.getString(3))));
						}
						
						// add cycle accidents data to cycleAccidents series
						rs = MapDao.getAllColumns("select " + "month(accidentdate), year(accidentdate), count(referenceNumber) "
								+ "from accidentinfo " + "where weatherConditions like '" + weather + "' and year(accidentdate) >= '"
								+ fromYear + "' and year(accidentdate) <= '" + toYear + "' and month(accidentdate) >= '" + fromMonth
								+ "' and month(accidentdate) <= '" + toMonth + "' and lower(typeOfVehicle) like '%cycle%' group by month(accidentdate), year(accidentdate)");
						
						cycleAccidents.setName("Cycle Accidents");
						while (rs.next()) {
							cycleAccidents.getData().add(new XYChart.Data<String, Number>(rs.getString(1) + "-" + rs.getString(2),
									Integer.parseInt(rs.getString(3))));
						}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		scatterChart.getData().add(carAccidents);
		scatterChart.getData().add(busAccidents);
		scatterChart.getData().add(taxiAccidents);
		scatterChart.getData().add(cycleAccidents);
		scatterChart.setAnimated(false);

	}
}
