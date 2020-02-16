package test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import utilities.ExcelReader;
import utilities.FilePathBuilder;
import utilities.FrameworkConstants;

/**
 * 
 * This is class to prepare the test data
 * using DataProvider by passing sheetName as parameter at suite level(.xml)
 * 
 */

public class BaseTest {
	private final Logger logger = LoggerFactory.getLogger(BaseTest.class);
	Map<String, List<Map<String, String>>> testCasesWithSamples;

	/**
	 * This method is to initiate and get test data for service calls
	 * 
	 * @throws Exception
	 */
	@Parameters({"testDataSheet"})
	@BeforeSuite
	public void doInitiateTest(String sheetName) throws Exception {
		FilePathBuilder fpb = new FilePathBuilder(FrameworkConstants.API_TEST_DATA);
		fpb.setParentDirectory(FrameworkConstants.TESTDATA_DIRECTORY);

		String testDataPath = fpb.getFilePath();
		logger.debug("Environment Properties Path {}", testDataPath);

		ExcelReader ex = new ExcelReader(testDataPath, sheetName);
		testCasesWithSamples = ex.getTestCasesWithSamples();

		logger.debug("The test data samples are {}", testCasesWithSamples);
	}

	/**
	 * This is method to prepare test data using DataProvider
	 * 
	 * @param method
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "testData")
	public Object[][] testDataPreparation(Method method) throws Exception {
		logger.info("The {} initiated:", method.getName());
		List<Map<String, String>> samples = testCasesWithSamples.get(method.getName());
		Object[][] obj = null;
		int i = 0;
		if (samples == null) {
			obj = new Object[0][0];
		} else {
			obj = new Object[samples.size()][2];
		}

		for (Map<String, String> samp : samples) {
			obj[i][0] = samp;
			obj[i][1] = samp.get("Result");
			i++;
		}

		return obj;
	}
}
