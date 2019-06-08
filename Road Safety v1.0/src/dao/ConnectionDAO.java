package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;

/**
 * Class to handle Database
 * 
 * @author
 *
 */
public class ConnectionDAO {

	/**
	 * Method to get connection handle to DB
	 * 
	 * @return Connection
	 */
	public static Connection getConnection() {
		Connection conn;
		try {
			conn = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPassword);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Method to execute the query in Database
	 * 
	 * @param query
	 * @return void
	 */
	public static void executeQuery(String query) {
		Connection conn = getConnection();
		Statement st;
		try {
			st = conn.createStatement();
			st.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> yearsAvailable() {
		ArrayList<String> data = new ArrayList<String>();
		ResultSet rs = MapDao.getAllColumns("Select distinct year from accidentinfo");
		try {
			while (rs.next())
				data.add(rs.getString(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public static ArrayList<String> getPostcodes() {
		ArrayList<String> postcodes = new ArrayList<String>();
		ResultSet rs = MapDao.getAllColumns("Select distinct postcode from Postcodes");
		try {
			while (rs.next())
				postcodes.add(rs.getString(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return postcodes;
	}

	public static ArrayList<String> getLatLongForPostcode(String postcode) {
		ArrayList<String> latLong = new ArrayList<String>();
		ResultSet rs = MapDao.getAllColumns(
				"Select Latitude, Longitude from Postcodes where Postcode='" + postcode + "'" + " limit 1");
		try {
			while (rs.next()) {
				latLong.add(rs.getString(1));
				latLong.add(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return latLong;
	}

	public static ArrayList<String> getUserNamesFromDB() {
		ArrayList<String> users = new ArrayList<>();
		ResultSet rs = MapDao.getAllColumns("Select userName from Person");
		try {
			while (rs.next())
				users.add(rs.getString(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

	public static ArrayList<String> speedsAvailable() {
		int speed = 0;
		ArrayList<String> data = new ArrayList<String>();
		ResultSet rs = MapDao.getAllColumns("Select distinct speed from accidentinfo");
		try {
			while (rs.next()) {
				speed = rs.getInt(1);
				if (speed > 0)
					data.add(speed + "");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;

	}

	public static ArrayList<String> weathersAvailable() {
		ArrayList<String> data = new ArrayList<String>();
		ResultSet rs = MapDao.getAllColumns("Select distinct weatherConditions from accidentinfo");
		try {
			while (rs.next())
				data.add(rs.getString(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;

	}

	public static ArrayList<String> agesAvailable() {
		int age = 0;
		ArrayList<String> data = new ArrayList<String>();
		ResultSet rs = MapDao.getAllColumns("Select distinct ageOfCasualty from accidentinfo");
		try {
			while (rs.next()) {
				age = Integer.parseInt(rs.getString(1));
				if (age >= 0)
					data.add("" + age);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static ArrayList<String> getQuestions() {
		ArrayList<String> data = new ArrayList<String>();
		try {
			//
			ResultSet rs = MapDao.getAllColumns("select question from questions");
			while (rs.next()) {
				data.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public static String getFact(String whereClause) {
		String data = "";
		try {
			//
			ResultSet rs = MapDao.getAllColumns("select count(*) from accidentinfo " + whereClause);
			rs.next();
			data = rs.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public static boolean submitQuestion(String question) {
		boolean success = false;
		try {
			Connection conn = ConnectionDAO.getConnection();
			String query = " insert into questions(question, create_timestamp) values (?, curdate())";

			// create the mysql insert prepared statement
			PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setString(1, question);

			// execute the prepared statement
			if (preparedStmt.executeUpdate() > 0) {
				success = true;
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	public static ArrayList<String> getCasualtiesForPredData(int size) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			//
			ResultSet rs = MapDao.getAllColumns(
					"select month(accidentdate), year(accidentdate) from accidentinfo order by year(accidentdate) desc, month(accidentdate) desc");
			rs.next();
			int month = rs.getInt(1);
			int year = rs.getInt(2);
			for (int i = 0; i < size; i++) {
				if (month > 12) {
					month = 01;
					year++;
				}
				data.add(month + "-" + year);
				month++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public static LinkedHashMap<String, Integer> getVehicleAccidentsData(String year, String vehicleType) {
		LinkedHashMap<String, Integer> data = new LinkedHashMap<String, Integer>();
		Connection conn = ConnectionDAO.getConnection();
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement("select month(accidentdate), year(accidentdate), count(referenceNumber) "
					+ "from accidentinfo where year(accidentdate) = '" + year + "' and lower(typeOfVehicle) like '%"
					+ vehicleType.toLowerCase() + "%' group by month(accidentdate), year(accidentdate) "
					+ "order by month(accidentdate)");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				data.put(resultSet.getString(1) + "-" + resultSet.getInt(2), Integer.parseInt(resultSet.getString(3)));
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public static Hashtable<String, Integer> getScatterData(String col) {

		Hashtable<String, Integer> data = new Hashtable<String, Integer>();
		Connection conn = ConnectionDAO.getConnection();
		PreparedStatement statement = null;
		if (col.equals("roadSurface"))
			col = "";
		else if (col.equals("Weather"))
			col = "weatherConditions";
		else
			col = "lightingConditions";
		try {
			statement = conn.prepareStatement("select " + col + ", count(*) from accidentinfo group by " + col);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				data.put(resultSet.getString(1), resultSet.getInt(2));
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public static Hashtable<String, Integer> getDataForChart1() {

		Hashtable<String, Integer> data = new Hashtable<String, Integer>();
		Connection conn = ConnectionDAO.getConnection();
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(
					"select T.vehicle, FORMAT(count(*) * 100 / (Select count(*) from accident.accidentinfo), 0) from \n"
							+ "(select \n" + "case \n" + "WHEN UPPER(typeOfVehicle) like '%BUS%' THEN 'Bus'\n"
							+ "WHEN UPPER(typeOfVehicle) like '%CAR%' THEN 'Car'\n"
							+ "WHEN UPPER(typeOfVehicle) like '% CYCLE%' THEN 'Cycle'\n"
							+ "WHEN UPPER(typeOfVehicle) like '%MOTORCYCLE%' THEN 'Motorcycle'\n"
							+ "ELSE 'Other' END as vehicle\n" + "from accident.accidentinfo\n" + ") T\n"
							+ "group by T.vehicle");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				data.put(resultSet.getString(1), resultSet.getInt(2));
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public static Hashtable<String, Integer> getDataForChart2() {

		Hashtable<String, Integer> data = new Hashtable<String, Integer>();
		Connection conn = ConnectionDAO.getConnection();
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(
					"select T.people, FORMAT(count(*) * 100 / (Select count(*) from accident.accidentinfo), 2) from \n"
							+ "(select \n" + "case \n" + "WHEN ageOfCasualty BETWEEN 0 AND 12 THEN 'Child'\n"
							+ "WHEN ageOfCasualty BETWEEN 13 AND 17 THEN 'Teenager'\n"
							+ "WHEN ageOfCasualty BETWEEN 18 AND 35 THEN 'Young-Adult'\n"
							+ "WHEN ageOfCasualty BETWEEN 36 AND 45 THEN 'Adult'\n"
							+ "WHEN ageOfCasualty BETWEEN 46 AND 65 THEN 'Middle-Aged'\n"
							+ "WHEN ageOfCasualty > 65 THEN 'Senior'\n" + "END as people\n"
							+ "from accident.accidentinfo\n" + ") T\n" + "WHERE T.people IS NOT NULL\n"
							+ "group by T.people\n" + "");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				data.put(resultSet.getString(1), resultSet.getInt(2));
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public static LinkedHashMap<String, Integer> getDataForChart3() {

		LinkedHashMap<String, Integer> data = new LinkedHashMap<String, Integer>();
		Connection conn = ConnectionDAO.getConnection();
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(
					"select year, count(referenceNumber) from accidentinfo group by year order by year");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				data.put(resultSet.getString(1), resultSet.getInt(2));
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public static ArrayList<LinkedHashMap<String, Integer>> getDataForChart4() {

		ArrayList<LinkedHashMap<String, Integer>> data = new ArrayList<>();
		LinkedHashMap<String, Integer> males = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> females = new LinkedHashMap<String, Integer>();

		Connection conn = ConnectionDAO.getConnection();
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(
					"select year, count(sexOfCasualty) from accidentinfo where sexOfCasualty='Male' group by year, sexOfCasualty");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				males.put(resultSet.getString(1), resultSet.getInt(2));
			}
			statement = conn.prepareStatement(
					"select year, count(sexOfCasualty) from accidentinfo where sexOfCasualty='Female' group by year, sexOfCasualty");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				females.put(resultSet.getString(1), resultSet.getInt(2));
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		data.add(males);
		data.add(females);
		return data;
	}

	public static String getGuestNo() {
		String guestNo = null;
		ResultSet rs = MapDao.getAllColumns("select max(guestid) from guest");
		try {
			rs.next();
			guestNo = "" + (rs.getInt(1) + 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return guestNo;
	}

	public static boolean loginGuest(String guestNo, String userName, String firstName, LocalDate dateOfBirth,
			String sex) {
		boolean success = false;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String dob = dateOfBirth.format(formatter);

		try {
			Connection conn = ConnectionDAO.getConnection();
			String query = " insert into guest (GuestID, firstName, username, gender, age) values (?, ?, ?, ?, ?)";

			// create the mysql insert prepared statement
			PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setString(1, guestNo);
			preparedStmt.setString(2, firstName);
			preparedStmt.setString(3, userName);
			preparedStmt.setString(4, sex);
			preparedStmt.setString(5, dob);

			// execute the prepared statement
			if (preparedStmt.executeUpdate() > 0) {
				success = true;
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	public static boolean register(String firstName, String surName, String gender, String contact, String address,
			String userName, String postalCode, String email, String password, String confirmPassword) {
		boolean success = false;
		try {
			Connection conn = ConnectionDAO.getConnection();
			String query = " insert into person "
					+ "(firstName, surName, gender, contact, address,userName,postalCode,email,password,confirmPassword)"
					+ " values (?, ?, ?, ?, ?,?,?,?,?,?)";

			// create the mysql insert prepared statement
			PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setString(1, firstName);
			preparedStmt.setString(2, surName);
			preparedStmt.setString(3, gender);
			preparedStmt.setString(4, contact);
			preparedStmt.setString(5, address);
			preparedStmt.setString(6, userName);
			preparedStmt.setString(7, postalCode);
			preparedStmt.setString(8, email);
			preparedStmt.setString(9, password);
			preparedStmt.setString(10, confirmPassword);

			// execute the prepared statement
			if (preparedStmt.executeUpdate() > 0) {
				success = true;
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	public static boolean login(String username, String password) {
		boolean success = false;
		PreparedStatement statement;

		try {

			Connection conn = getConnection();
			statement = conn
					.prepareStatement("select userName from accident.person where userName = ? and password= ?");

			statement.setString(1, username);
			statement.setString(2, password);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next())
				success = true;

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

}
