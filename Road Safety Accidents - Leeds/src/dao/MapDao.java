package dao;

import com.google.gson.Gson;
import map.LocationVO;
import uk.me.jstott.jcoord.LatLng;
import uk.me.jstott.jcoord.OSRef;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class to handle map screen queries
 * 
 * @author
 *
 */
public class MapDao {

	/**
	 * Method to get all the location coordinates using a query
	 * 
	 * @param query
	 * @return
	 */
	public static ResultSet getAllColumns(String query) {
		System.out.println(query);
		try {
			PreparedStatement statement = ConnectionDAO.getConnection().prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			ConnectionDAO.getConnection().close();
			return resultSet;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Method to get all locations
	 * 
	 * @return
	 */
	public static String getAllLocations() {
		try {
			ArrayList<LocationVO> locationList = new ArrayList<LocationVO>();

			PreparedStatement statement = ConnectionDAO.getConnection()
					.prepareStatement("select easting,northing from accidentinfo");
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next() == false) {

			} else {
				do {
					locationList.add(getLocationVo(resultSet));

				} while (resultSet.next());
			}
			ConnectionDAO.getConnection().close();
			return getLocations(locationList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Method to convert northing/easting to lat/long
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public static LocationVO getLocationVo(ResultSet resultSet) throws SQLException {
		String easting = resultSet.getString("easting");
		String northing = resultSet.getString("northing");
		double eastingDouble = Double.parseDouble(easting);
		double northingDouble = Double.parseDouble(northing);
		LocationVO l1 = new LocationVO();
		LatLng latLng = new OSRef(eastingDouble, northingDouble).toLatLng();
		l1.setLat(latLng.getLat());
		l1.setLng(latLng.getLng());
		return l1;

	}

	public static String getLocations(ArrayList<LocationVO> locationList) {
		// todao get update
		Gson gson = new Gson();

		return gson.toJson(locationList);
	}
}
