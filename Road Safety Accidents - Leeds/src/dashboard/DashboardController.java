package dashboard;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.MapDao;

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
    	populateChart1();
    	populateChart2();
    	populateChart3();
    	populateChart4();
    }
    
    private void populateChart4()
    {
    	ResultSet rs = null;
    	XYChart.Series<String, Number> driver, passenger, padestrian = null;
    	final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        chart4.setTitle("Yearly Casulties");
        xAxis.setLabel("Year");
        yAxis.setLabel("Casualty");

		driver = new XYChart.Series<String, Number>();
		passenger = new XYChart.Series<String, Number>();
		padestrian = new XYChart.Series<String, Number>();
		try {
			//
			rs = MapDao.getAllColumns("select year, count(casualtyClass) from accidentinfo where casualtyClass = 'Driver' group by year");
			driver.setName("Driver");
			while (rs.next()) {
				driver.getData().add(new XYChart.Data<String, Number>(rs.getString(1), Integer.parseInt(rs.getString(2))));
			}
			
			//
			rs = MapDao.getAllColumns("select year, count(casualtyClass) from accidentinfo where casualtyClass = 'Passenger' group by year");
			passenger.setName("Passenger");
			while (rs.next()) {
				passenger.getData().add(new XYChart.Data<String, Number>(rs.getString(1), Integer.parseInt(rs.getString(2))));
			}
			
			//
			rs = MapDao.getAllColumns("select year, count(casualtyClass) from accidentinfo where casualtyClass = 'Pedestrian' group by year");
			padestrian.setName("Pedestrian");
			while (rs.next()) {
				padestrian.getData().add(new XYChart.Data<String, Number>(rs.getString(1), Integer.parseInt(rs.getString(2))));
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		chart4.getData().addAll(driver, passenger, padestrian);
		chart4.setAnimated(false);
    }
		
    private void populateChart3()
    {
    	ResultSet rs = null;
    	XYChart.Series<String, Number> totalCasualties = null;
    	final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        //final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        chart3.setTitle("Yearly Casulties - Total");
        xAxis.setLabel("Year");
        yAxis.setLabel("Casualty");

		totalCasualties = new XYChart.Series<String, Number>();
		
		try {
			//
			rs = MapDao.getAllColumns("select year, count(referenceNumber) from accidentinfo group by year");
			totalCasualties.setName("Total Casualties");
			while (rs.next()) {
				totalCasualties.getData().add(new XYChart.Data<String, Number>(rs.getString(1), Integer.parseInt(rs.getString(2))));
			}
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		chart3.getData().addAll(totalCasualties);
		chart3.setAnimated(false);
		
		
    }

    
    private void populateChart1()
    {
    	ResultSet rs = null;
    	XYChart.Series<String, Number> maleCasualties, femaleCasualties = null;
    	final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        //final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        chart1.setTitle("Yearly Casulties - Gender");
        xAxis.setLabel("Year");
        yAxis.setLabel("Casualty");

		maleCasualties = new XYChart.Series<String, Number>();
		femaleCasualties = new XYChart.Series<String, Number>();
		try {
			//
			rs = MapDao.getAllColumns("select year, count(sexOfCasualty) from accidentinfo where sexOfCasualty='Male' group by year, sexOfCasualty");
			maleCasualties.setName("Male");
			while (rs.next()) {
				maleCasualties.getData().add(new XYChart.Data<String, Number>(rs.getString(1), Integer.parseInt(rs.getString(2))));
			}
			
			// populate sex combo box
			rs = MapDao.getAllColumns("select year, count(sexOfCasualty) from accidentinfo where sexOfCasualty='Female' group by year, sexOfCasualty");
			femaleCasualties.setName("Female");
			while (rs.next()) {
				femaleCasualties.getData().add(new XYChart.Data<String, Number>(rs.getString(1), Integer.parseInt(rs.getString(2))));
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		chart1.getData().addAll(maleCasualties, femaleCasualties);
		chart1.setAnimated(false);
		
		
    }
    
    private void populateChart2()
    {
    	ResultSet rs = null;
    	XYChart.Series<String, Number> slightCasualties, seriousCasualties, fatalCasualties = null;
    	final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        //final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        chart2.setTitle("Yearly Casulties - Type");
        xAxis.setLabel("Year");
        yAxis.setLabel("Casualty");

		slightCasualties = new XYChart.Series<String, Number>();
		seriousCasualties = new XYChart.Series<String, Number>();
		fatalCasualties = new XYChart.Series<String, Number>();
		try {
			//
			rs = MapDao.getAllColumns("select year, count(casualtySeverity) from accidentinfo where casualtySeverity = 'Slight' group by year, casualtySeverity");
			slightCasualties.setName("Slight");
			while (rs.next()) {
				slightCasualties.getData().add(new XYChart.Data<String, Number>(rs.getString(1), Integer.parseInt(rs.getString(2))));
			}
			
			// populate sex combo box
			rs = MapDao.getAllColumns("select year, count(casualtySeverity) from accidentinfo where casualtySeverity = 'Serious' group by year, casualtySeverity");
			seriousCasualties.setName("Serious");
			while (rs.next()) {
				seriousCasualties.getData().add(new XYChart.Data<String, Number>(rs.getString(1), Integer.parseInt(rs.getString(2))));
			}
		
		// populate sex combo box
					rs = MapDao.getAllColumns("select year, count(casualtySeverity) from accidentinfo where casualtySeverity = 'Fatal' group by year, casualtySeverity");
					seriousCasualties.setName("Fatal");
					while (rs.next()) {
						fatalCasualties.getData().add(new XYChart.Data<String, Number>(rs.getString(1), Integer.parseInt(rs.getString(2))));
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		chart2.getData().addAll(slightCasualties, seriousCasualties, fatalCasualties);
		chart2.setAnimated(false);
		
		
    }
}
