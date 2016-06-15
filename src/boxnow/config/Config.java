package boxnow.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Config {
	
	private Properties configProperties;
	
	public Config(){
		
	}
	
	public void initConfig(){
		
	}
	
	public void getPropertiesFromFile(){
		try {
			
			File file = new File("src/boxnow/config/Config.properties");
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
			setConfigProperties(properties);
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public Properties getConfigProperties() {
		return configProperties;
	}

	public void setConfigProperties(Properties configProperties) {
		this.configProperties = configProperties;
	}
	


}
