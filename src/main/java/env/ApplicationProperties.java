package env;


import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utilities.FileOperations;
import utilities.FilePathBuilder;
import utilities.FrameworkConstants;

/**
 * 
 * This class is the implementation of application properties of AUT
 */

public class ApplicationProperties {

	private final Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);

	private String serverIP;
	private String port;
	private String protocol;

	private Map<String, String> additionalProps = null;

	protected ApplicationProperties() {
		additionalProps = new HashMap<>(5);
	}

	protected void loadProperties() {
		FilePathBuilder fpb = new FilePathBuilder(FrameworkConstants.ENVIORNMENT_PROPS);
		fpb.setParentDirectory(FrameworkConstants.PROPERTIES_DIRECTORY);

		String envProps = fpb.getFilePath();
		logger.debug("Environment Properties Path {}", envProps);

		FileOperations fileOps = new FileOperations(new File(envProps));
		Map<String, String> props = fileOps.getPropValuesInMap();

		if (props == null) {
			logger.error("Failed to read the properties for the application from resouce: " + envProps);
			return;
		}

		for (Entry<String, String> entry : props.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			if (FrameworkConstants.APPLICATION_PROTOCOL.equals(key))
				setProtocol(value);
			else if (FrameworkConstants.APPLICATION_SERVER_IP.equals(key))
				setServerIP(value);
			else if (FrameworkConstants.APPLICATION_SERVER_PORT.equals(key))
				setPort(value);
			else
				setProperty(key, value);
		}
	}
	
	public String getServerIP() {
		return serverIP;
	}

	private void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public String getPort() {
		return port;
	}

	private void setPort(String portStr) {
		if (StringUtils.isEmpty(portStr)) {
			logger.info("No Port is specified in the properties file...");
		} else {
			this.port = portStr;
		}
	}

	public String getProtocol() {
		return protocol;
	}

	private void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getBaseURL() {
		String url = String.format("%s://%s", getProtocol(), getServerIP());
		String appPort = getPort();
		if (!StringUtils.isEmpty(appPort))
			url = String.format("%s:%s", url, appPort);
		
		return url;
	}

	private void setProperty(String key, String value) {
		additionalProps.put(key, value);
	}

	public String getPropertyValue(String key) {
		return additionalProps.get(key);
	}
}
