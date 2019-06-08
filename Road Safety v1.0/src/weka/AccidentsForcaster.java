package weka;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import dao.Constants;
import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.timeseries.WekaForecaster;
import weka.core.Instances;
import weka.core.converters.DatabaseLoader;

public class AccidentsForcaster {

	
	
	public static ArrayList<Integer> getMonthlyPredictions() {
		
		ArrayList<Integer> res = new ArrayList<Integer>();
		InputStream is = null;
		try {
			Instances ins = buildMonthlyArff();
			
			ClassLoader classloader = new AccidentsForcaster().getClass().getClassLoader();
			is = classloader.getResourceAsStream("resources/forcast_model.model");
			

			WekaForecaster forecaster = (WekaForecaster) weka.core.SerializationHelper.read(is);
			forecaster.primeForecaster(ins);
			List<List<NumericPrediction>> forecast = forecaster.forecast(12, System.out);
			
			for (int i = 0; i < forecast.size(); i++) {
		        res.add((int)forecast.get(i).get(0).predicted());
				List<NumericPrediction> predsAtStep = forecast.get(i);
		        for (int j = 0; j < 1 ; j++) {
		          NumericPrediction predForTarget = predsAtStep.get(j);
		          System.out.print("" + predForTarget.predicted() + " ");
		        }
		        System.out.println();
		      }
			is.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}
	
	
	public static Instances buildMonthlyArff() {
		Instances wekaData  = null;
		try {
			DatabaseLoader databaseLoader = new DatabaseLoader();
			databaseLoader.setUrl(Constants.dbUrl);
			databaseLoader.setUser(Constants.dbUser);
			databaseLoader.setPassword(Constants.dbPassword);
			
			databaseLoader.setQuery("select month(accidentdate) as month, year(accidentdate) as year, count(*) as accidents from accident.accidentinfo group by month(accidentdate), year(accidentdate) order by year(accidentdate), month(accidentdate)");
			
			wekaData = databaseLoader.getDataSet();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wekaData;
		
	}
	
	/*public static void main(String...s) {
		ArrayList<Integer> data = getMonthlyPredictions();
		System.out.println(data);
	}*/
	
}
