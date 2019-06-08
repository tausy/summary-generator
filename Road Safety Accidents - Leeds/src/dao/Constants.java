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

	// opencage api key for address to lat/long conversion
	public static final String OPENCAGE_API_KEY = "4d06204649b24affa0cc39e02216a2d6";
	
	// Text box text compare screen - place the text below in double quotes
	public static final String COMPARE_TEXT_BOX = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
	
	// Text box text predict screen - place the text below in double quotes
	public static final String PREDICT_TEXT_BOX = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

	public static final String REGEX_NAME = "[A-Za-z ]*";
	public static final String REGEX_GENDER = "[A-Za-z]*";
	public static final String REGEX_PASSWORD = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	public static final String REGEX_EMAIL = "^([\\\\w-\\\\.]+){1,64}@([\\\\w&&[^_]]+){2,255}.[a-z]{2,}$";
	public static final String REGEX_PHONE = "[0-9]*";
	
}
