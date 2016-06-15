package boxnow.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BoxNow extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		System.out.println("Starting BoxNow");
		
		Font.loadFont(getClass().getResource("/boxnow/resources/SFDigitalReadout-Light.ttf").toExternalForm(),10);
				
		StackPane root = FXMLLoader.load(getClass().getResource("BoxNow.fxml"));
		root.setId("root_node");
		Rectangle rect = new Rectangle(1200,400);
		rect.setArcHeight(60.0);
		rect.setArcWidth(60.0);

		root.setClip(rect);
		Scene scene = new Scene(root,1200,400);
		stage.initStyle(StageStyle.TRANSPARENT);
		scene.setFill(Color.TRANSPARENT);
		stage.setScene(scene);
		stage.getScene().getStylesheets().add("boxnow/main/BoxNowStyle.css");
		stage.show();
		
	}

}
