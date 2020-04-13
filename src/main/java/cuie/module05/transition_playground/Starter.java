package cuie.module05.transition_playground;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Dieter Holz
 */
public class Starter extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent rootPanel = new TransitionPlayground();

		Scene scene = new Scene(rootPanel);

		primaryStage.setTitle("Transition Playground");
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
