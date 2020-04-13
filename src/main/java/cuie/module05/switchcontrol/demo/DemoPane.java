package cuie.module05.switchcontrol.demo;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import cuie.module05.switchcontrol.Switch;

/**
 * @author Dieter Holz
 */
public class DemoPane extends BorderPane {

    private final PresentationModel pm;

    // the custom control
	private Switch switchControl;

    // all standard demo controls
    private CheckBox checkBox;

    public DemoPane(PresentationModel pm) {
        this.pm = pm;
        initializeControls();
		layoutControls();
		setupBindings();
	}

	private void initializeControls() {
		setPadding(new Insets(10));

        switchControl = new Switch();

		checkBox = new CheckBox();
	}

	private void layoutControls() {
		setCenter(switchControl);

		VBox box = new VBox(10, new Label("Switch Properties"), checkBox);
		box.setPadding(new Insets(10));
		setRight(box);
	}

	private void setupBindings() {
		checkBox.selectedProperty().bindBidirectional(pm.onProperty());

		switchControl.onProperty().bindBidirectional(pm.onProperty());
	}

}
