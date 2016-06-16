package boxnow.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Config {
	
	private Properties configProperties;
	
	public Config(){
		
	}
	
	public void displayConfig(){
		try {
			Font.loadFont(getClass().getResource("/boxnow/resources/SFDigitalReadout-Light.ttf").toExternalForm(),10);
			Stage confStage = new Stage();
			StackPane config = FXMLLoader.load(getClass().getResource("Config.fxml"));
			config.setId("config_node");
			Rectangle rect = new Rectangle(400,600);
			rect.setArcHeight(60);
			rect.setArcWidth(60.0);
			config.setClip(rect);
			Scene confScene = new Scene(config,400,600);
			confStage.initStyle(StageStyle.TRANSPARENT);
			confScene.setFill(Color.TRANSPARENT);
			confStage.setScene(confScene);
			confStage.getScene().getStylesheets().add("boxnow/config/ConfigStyle.css");
			confStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
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
