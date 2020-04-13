package cuie.module02.resizing_b;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * @author Dieter Holz
 */
public class ResizingTemplateStarter extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Region rootPanel = new ResizingTemplatePane();

		Scene scene = new Scene(rootPanel);

		primaryStage.minHeightProperty().bind(Bindings.max(0, primaryStage.heightProperty().subtract(scene.heightProperty()).add(rootPanel.minHeightProperty())));
		primaryStage.minWidthProperty().bind(Bindings.max(0, primaryStage.widthProperty().subtract(scene.widthProperty()).add(rootPanel.minWidthProperty())));
		primaryStage.setTitle("Resizing Demo");
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
