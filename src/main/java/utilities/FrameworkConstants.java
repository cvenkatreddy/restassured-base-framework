package utilities;

/**
 * 
 * This class is for all constants used across framework
 */

public class FrameworkConstants {

	public static final String ENVIORNMENT_PROPS = "applicationUnderTest.properties";
	public static final String PROPERTIES_DIRECTORY = "properties";
	public static final String TESTDATA_DIRECTORY = "testdata";

	public static final String APPLICATION_SERVER_IP = "serverIP";
	public static final String APPLICATION_PROTOCOL = "httpType";
	public static final String APPLICATION_SERVER_PORT = "port";

	public static final String API_TEST_DATA = "apitests.xlsx";
	public static final String STANDARD_EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
													 "[a-zA-Z0-9_+&*-]+)*@" + 
													 "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
													 "A-Z]{2,7}$";
}
