package utilities;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
* 
* ExcelReader is to read data from excel using POI to prepare test data
*/ 
public class ExcelReader {

	private Map<String, List<Map<String, String>>> testCasesWithSamples = new LinkedHashMap<String, List<Map<String, String>>>();
	private String path;
	private String sheetName;

	public ExcelReader(String path, String sheetName) throws Exception {
		this.path = path;
		this.sheetName = sheetName;
		readTestData();
	}

	public Map<String, List<Map<String, String>>> getTestCasesWithSamples() {
		return testCasesWithSamples;
	}

	public void readTestData() throws Exception {

		String resource = path;
		FileInputStream fis = new FileInputStream(resource);

		Workbook book = null;
		Sheet sheet = null;

		if (resource.endsWith("xlsx")) {
			book = new XSSFWorkbook(fis);
		} else if (resource.endsWith("xls")) {
			book = new HSSFWorkbook(fis);
		}

		sheet = book.getSheet(sheetName);

		List<String> headerList = null;
		List<String> dataList = null;
		Map<String, String> sample = null;
		List<Map<String, String>> samples = null;

		for (Row row : sheet) {

			if (row.getCell(0).toString().startsWith("Header")) {

				headerList = new ArrayList<String>();
				samples = new ArrayList<Map<String, String>>();

				for (Cell cell : row) {
					headerList.add(cell.toString());
				}

			} else {

				dataList = new ArrayList<String>();
				sample = new LinkedHashMap<String, String>();

				for (Cell cell : row) {
					dataList.add(cell.toString());
				}

				for (int i = 0; i < dataList.size(); i++) {
					sample.put(headerList.get(i), dataList.get(i));
				}
				samples.add(sample);

				if (testCasesWithSamples.get(dataList.get(0)) == null) {
					testCasesWithSamples.put(dataList.get(0), samples);
				}
			}
		}
	}
}
