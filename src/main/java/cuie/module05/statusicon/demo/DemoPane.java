package cuie.module05.statusicon.demo;

import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;

import cuie.module05.statusicon.StatusIcon;

/**
 * @author Dieter Holz
 */
public class DemoPane extends BorderPane {
    private final PresentationModel pm;

    private StatusIcon customControl;

	private ChoiceBox<StatusIcon.Status> statusField;

	public DemoPane(PresentationModel pm) {
        this.pm = pm;
        initializeControls();
		layoutControls();
		addBindings();
	}

	private void initializeControls() {
		setPadding(new Insets(10));

		customControl = new StatusIcon();

		statusField = new ChoiceBox<>();
		statusField.getItems().addAll(StatusIcon.Status.values());
	}

	private void layoutControls() {
		setMargin(statusField, new Insets(0, 0, 10, 10));

		setCenter(customControl);
		setRight(statusField);
	}

	private void addBindings() {
		statusField.valueProperty().bindBidirectional(pm.currentStatusProperty());

		customControl.statusProperty().bindBidirectional(pm.currentStatusProperty());
	}

}
