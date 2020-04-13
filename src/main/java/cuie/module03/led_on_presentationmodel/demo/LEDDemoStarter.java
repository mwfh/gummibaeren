package cuie.module03.led_on_presentationmodel.demo;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import cuie.module03.led_on_presentationmodel.demo.DemoUI;

public class LEDDemoStarter extends Application {

	@Override
	public void start(Stage primaryStage) {

		DemoPM pm = new DemoPM();

		Parent rootPanel = new DemoUI(pm); //Braucht Informationen was es visualisieren soll --> pm übergeben

		Scene scene = new Scene(rootPanel);

		primaryStage.setTitle("LED");
		primaryStage.setScene(scene);
		primaryStage.setWidth(300);
		primaryStage.setHeight(300);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
