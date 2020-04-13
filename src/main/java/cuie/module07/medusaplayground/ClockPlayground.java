package cuie.module07.medusaplayground;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import eu.hansolo.medusa.Clock;
import eu.hansolo.medusa.ClockBuilder;

/**
 * @author Dieter Holz
 */
public class ClockPlayground extends Application {
	private Clock clock;

	@Override
	public void init() {
		clock = ClockBuilder.create()
                            // todo: add your customization here
		                    .prefSize(400, 400)
		                    .skinType(Clock.ClockSkinType.PEAR)
		                    .running(true)

		                    .build();
	}

	@Override
	public void start(Stage stage) {
		HBox pane = new HBox(clock);

		pane.setPadding(new Insets(10));
		pane.setSpacing(20);

		Scene scene = new Scene(pane);

		stage.setTitle("Clock Playground");
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void stop() {
		System.exit(0);
	}

	public static void main(String[] args) {
		launch(args);
	}
}

