package cuie.module01.tablestyling;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.scenicview.ScenicView;

public class TableStylingStarter extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent rootPanel = new TableStylingPane();

		Scene scene = new Scene(rootPanel);

		primaryStage.setTitle("Styling von Table und Scrollbar");
		primaryStage.setScene(scene);
		primaryStage.setWidth(300);
		primaryStage.setHeight(300);

		ScenicView.show(scene); //Die Scene soll via ScenicView dargestellt werden (nützlich um Property-Namen herauszufinden zum Stylen)
		primaryStage.show(); //Die Scene soll normal dargestellt werden
	}

	public static void main(String[] args) {
		launch(args);
	}
}
