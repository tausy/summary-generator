package dao;

/**
 * Class to keep constants used through out the application
 * 
 * @author
 *
 */
public class Constants {

	// Class level constants
	public static final String dbUrl = "jdbc:mysql://localhost:3306/accident";
	public static final String dbUser = "root";
	public static final String dbPassword = "12345678";

	// Text box dashboard screen - place the text below in double quotes
	public static final String DASHBOARD_TEXT_BOX1 = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
	public static final String DASHBOARD_TEXT_BOX2 = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
	
	// Text box text predict screen - place the text below in double quotes
	public static final String PREDICT_TEXT_BOX = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

	
	// validation rules
	public static final String REGEX_USERNAME = "[A-za-z0-9]*";
	public static final String REGEX_NAME = "[A-Za-z ]*";
	public static final String REGEX_PASSWORD = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	public static final String REGEX_EMAIL = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";
	public static final String REGEX_PHONE = "[0-9]*";
	
	//stats name on facts screen
	public static String dummy1 = "fact1";
	public static String dummy2 = "fact2";
	public static String dummy3 = "fact3";
	public static String dummy4 = "fact4";
	public static String dummy5 = "fact5";
	public static String dummy6 = "fact6";
	public static String dummy7 = "fact7";
	public static String dummy8 = "fact8";
	public static String dummy9 = "fact9";
	public static String dummy10 = "fact10";
	public static String dummy11 = "fact11";
	
	//stat value on facts screen
	public static String dummyVal1 = "0";
	public static String dummyVal2 = "0";
	public static String dummyVal3 = "0";
	public static String dummyVal4 = "0";
	public static String dummyVal5 = "0";
	public static String dummyVal6 = "0";
	public static String dummyVal7 = "0";
	public static String dummyVal8 = "0";
	public static String dummyVal9 = "0";
	public static String dummyVal10 = "0";
	public static String dummyVal11 = "0";
	
	//preventions on facts screen
	public static final String FACTS_TEXT_BOX = "-> Lorem Ipsum is simply dummy text of the printing and typesetting industry. "
			+ "-> Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. "
			+ "-> It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. "
			+ "-> It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
}
