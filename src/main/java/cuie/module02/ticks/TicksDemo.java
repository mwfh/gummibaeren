package cuie.module02.ticks;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * @author Dieter Holz
 */
public class TicksDemo extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Region rootPanel = new TicksExample();

		Scene scene = new Scene(rootPanel);


		primaryStage.setTitle("Ticks");
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
