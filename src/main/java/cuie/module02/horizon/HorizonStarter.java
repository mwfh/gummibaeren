package cuie.module02.horizon;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * @author Dieter Holz
 */
public class HorizonStarter extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Region rootPanel = new Horizon();

		Scene scene = new Scene(rootPanel);


		primaryStage.setTitle("Horizon");
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
