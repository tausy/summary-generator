package map;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.MapDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import login.LoginManager;

public class MapController implements Initializable {
	@FXML
	private Button search;
	@FXML
	private CheckBox pedCheckBox;
	@FXML
	private CheckBox cycleChkBox;
	@FXML
	private CheckBox motorcycleChkBox;
	@FXML
	private CheckBox carChkBox;
	@FXML
	private CheckBox vanChkBox;
	@FXML
	private CheckBox busChkBox;
	@FXML
	private CheckBox lorryChkBox;
	@FXML
	private CheckBox otherChkBox;
	@FXML
	private CheckBox claimChkBox;
	@FXML
	private ComboBox<String> speedCombo;
	@FXML
	private ComboBox<String> yearCombo;
	@FXML
	private ComboBox<String> ageCombo;
	@FXML
	private ComboBox<String> sexCombo;
	@FXML
	private ComboBox<String> weatherCombo;
	@FXML
	private TextField searchMapText;

	@Override // This method is called by the FXMLLoader when initialization is complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		populateComboBoxes();
	}

	private void populateComboBoxes() {

		try {

			ResultSet rs = null;

			// populate sex combo box
			sexCombo.getItems().add("All");
			rs = MapDao.getAllColumns("Select distinct sexOfCasualty from accidentinfo");
			while (rs.next())
				sexCombo.getItems().add(rs.getString(1));
			

			// populate age combo box
			ageCombo.getItems().add("All");
			rs = MapDao.getAllColumns("Select distinct ageOfCasualty from accidentinfo");
			while (rs.next())
				if (rs.getInt(1) > 0)
					ageCombo.getItems().add(rs.getString(1));
			

			// populate year combo box
			yearCombo.getItems().add("All");
			rs = MapDao.getAllColumns("Select distinct year from accidentinfo");
			while (rs.next())
				yearCombo.getItems().add(rs.getString(1));
			

			// populate speed combo box
			speedCombo.getItems().add("All");
			rs = MapDao.getAllColumns("Select distinct speed from accidentinfo");
			while (rs.next())
				if (rs.getInt(1) > 0)
					speedCombo.getItems().add(rs.getString(1));
			

			// populate weather combo box
			weatherCombo.getItems().add("All");
			rs = MapDao.getAllColumns("Select distinct weatherConditions from accidentinfo");
			while (rs.next())
				weatherCombo.getItems().add(rs.getString(1));
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void search() {

		ArrayList<LocationVO> locationList = new ArrayList<LocationVO>();

		// Parameters from UI
		String pedestrian = pedCheckBox.isSelected() ? "Pedestrian" : "";
		String cycle = cycleChkBox.isSelected() ? "Cycle" : "";
		String motorcycle = motorcycleChkBox.isSelected() ? "Motorcycle" : "";
		String car = carChkBox.isSelected() ? "Car" : "";
		String van = vanChkBox.isSelected() ? "Van" : "";
		String bus = busChkBox.isSelected() ? "Bus" : "";
		String lorry = lorryChkBox.isSelected() ? "Lorry" : "";
		String other = otherChkBox.isSelected() ? "Other" : "";
		String claim = claimChkBox.isSelected() ? "Claims" : "";
		String speed = speedCombo.getValue();
		String year = yearCombo.getValue();
		String age = ageCombo.getValue();
		String sex = sexCombo.getValue();
		String weather = weatherCombo.getValue();

		// Base query
		String baseQuery = null;

		if (pedestrian.equals("") && cycle.equals("") && motorcycle.equals("") && car.equals("") && van.equals("")
				&& bus.equals("") && lorry.equals("") && other.equals("") && claim.equals("") && speed == null
				&& year == null && age == null && sex == null && weather == null) {
			baseQuery = "Select * from accidentinfo";
		}

		baseQuery = "Select easting, northing from accidentinfo where";

		if (!pedestrian.equals("")) {
			baseQuery = baseQuery + " lower(CasualtyClass) = 'pedestrian'";
		}

		if (!cycle.equals("")) {
			baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
					? baseQuery + " lower(TypeOfVehicle) = 'cycle'"
					: baseQuery + " OR " + "lower(TypeOfVehicle) = 'cycle'";
		}

		if (!motorcycle.equals("")) {
			baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
					? baseQuery + " lower(TypeOfVehicle) like '%motorcycle%'"
					: baseQuery + " OR lower(TypeOfVehicle) like '%motorcycle%'";
		}

		if (!car.equals("")) {
			baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
					? baseQuery + " lower(TypeOfVehicle) like '%car%'"
					: baseQuery + " OR lower(TypeOfVehicle) like '%car%'";
		}

		if (!van.equals("")) {
			baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
					? baseQuery + " lower(TypeOfVehicle) like '%van%'"
					: baseQuery + " OR lower(TypeOfVehicle) like '%van%'";
		}

		if (!bus.equals("")) {
			baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
					? baseQuery + " lower(TypeOfVehicle) like '%bus%'"
					: baseQuery + " OR lower(TypeOfVehicle) like '%bus%'";
		}

		if (!lorry.equals("")) {
			baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
					? baseQuery + " lower(TypeOfVehicle) like '%lorry%'"
					: baseQuery + " OR lower(TypeOfVehicle) like '%lorry%'";
		}

		if (!other.equals("")) {
			baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
					? baseQuery + " lower(TypeOfVehicle) like '%other%'"
					: baseQuery + " OR lower(TypeOfVehicle) like '%other%'";
		}

		if (!claim.equals("")) {
			baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
					? baseQuery + " lower(TypeOfVehicle) like '%claim%'"
					: baseQuery + " OR lower(TypeOfVehicle) like '%claim%'";
		}

		if (speed != null) {
			if (!speed.equals("All")) {
				baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
						? baseQuery + " speed = '" + speed + "'"
						: baseQuery + " AND speed = '" + speed + "'";
			} else {
				baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
						? baseQuery + " speed like '%'"
						: baseQuery + " AND speed like '%'";
			}
		}

		if (year != null) {
			if (!year.equals("All")) {
				baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
						? baseQuery + " year = '" + year + "'"
						: baseQuery + " AND year = '" + year + "'";
			} else {
				baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
						? baseQuery + " year like '%'"
						: baseQuery + " AND year like '%'";
			}
		}

		if (age != null) {
			if (!age.equals("All")) {
				baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
						? baseQuery + " ageOfCasualty = '" + age + "'"
						: baseQuery + " AND ageOfCasualty = '" + age + "'";
			} else {
				baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
						? baseQuery + " ageOfCasualty like '%'"
						: baseQuery + " AND ageOfCasualty like '%'";
			}
		}

		if (sex != null) {
			if (!sex.equals("All")) {
				baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
						? baseQuery + " sexOfCasualty = '" + sex + "'"
						: baseQuery + " AND sexOfCasualty = '" + sex + "'";
			} else {
				baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
						? baseQuery + " sexOfCasualty like '%'"
						: baseQuery + " AND sexOfCasualty like '%'";
			}
		}

		if (weather != null) {
			if (!weather.equals("All")) {
				baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
						? baseQuery + " weatherConditions ='" + weather + "'"
						: baseQuery + " AND weatherConditions ='" + weather + "'";
			} else {
				baseQuery = baseQuery.substring(baseQuery.lastIndexOf(' ') + 1).equals("where")
						? baseQuery + " weatherConditions like '%'"
						: baseQuery + " AND weatherConditions like '%'";
			}
		}

		ResultSet resultSet = MapDao.getAllColumns(baseQuery);

		if (resultSet != null) {
			try {

				while (resultSet.next()) {

					locationList.add(MapDao.getLocationVo(resultSet));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		LoginManager.browser.webEngine.executeScript("clearMarkers()");
		LoginManager.browser.webEngine.executeScript("initMap()");
		String script = "drawMap(" + MapDao.getLocations(locationList) + ")";
		LoginManager.browser.webEngine.executeScript(script);
	}

}
