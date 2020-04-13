package cuie.module07.controllibrary.demo;

import cuie.module07.controllibrary.SkinType;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

import cuie.module07.controllibrary.NumberRangeControl;

/**
 * @author Dieter Holz
 */
public class DemoPane extends BorderPane {
    private final PresentationModel pm;

    private NumberRangeControl customControl;

	private TextField titleField;
	private TextField valueField;
	private Label     valueLabel;
	private TextField unitField;
	private TextField minValueField;
	private TextField maxValueField;
	private CheckBox  isAnimated;


	public DemoPane(PresentationModel pm) {
        this.pm = pm;
        initializeControls();
		layoutControls();
		addBindings();
	}

	private void initializeControls() {
		setPadding(new Insets(10));

		//Hier festlegen welcher Typ das grosse CC hat
		customControl = new NumberRangeControl(SkinType.PIE);

        titleField    = new TextField();
        valueField    = new TextField();
        valueLabel    = new Label();
        unitField     = new TextField();
        minValueField = new TextField();
        maxValueField = new TextField();
        isAnimated    = new CheckBox();
    }

    //das auskommentierte funktioniert nicht.. Dann wird 2x das gleiche CC angezeigt
	private void layoutControls() {
		VBox box = new VBox(/*new NumberRangeControl(),*/ new NumberRangeControl(SkinType.LINEAR), titleField, valueField, valueLabel, unitField, new Label("min, max Value:"), minValueField, maxValueField, new Label("animated:"), isAnimated);
		box.setSpacing(10);
		box.setPadding(new Insets(20));

		setCenter(customControl);
		setRight(new ScrollPane(box));
	}

	private void addBindings() {
        titleField.textProperty().bindBidirectional(pm.titleProperty());
        unitField.textProperty().bindBidirectional(pm.unitProperty());
        valueField.textProperty().bindBidirectional(pm.valueProperty(), new NumberStringConverter());
        valueLabel.textProperty().bind(pm.valueProperty().asString("%.2f"));
        minValueField.textProperty().bindBidirectional(pm.minProperty(), new NumberStringConverter());
        maxValueField.textProperty().bindBidirectional(pm.maxProperty(), new NumberStringConverter());
        isAnimated.selectedProperty().bindBidirectional(pm.animatedProperty());

        customControl.titleProperty().bindBidirectional(pm.titleProperty());
        customControl.unitProperty().bindBidirectional(pm.unitProperty());
        customControl.valueProperty().bindBidirectional(pm.valueProperty());
        customControl.minValueProperty().bindBidirectional(pm.minProperty());
        customControl.maxValueProperty().bindBidirectional(pm.maxProperty());
        customControl.animatedProperty().bindBidirectional(pm.animatedProperty());
	}

}
