package cuie.module05.slimvaluedisplay.demo;

import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

import cuie.module05.slimvaluedisplay.SlimValueDisplay;

/**
 * @author Dieter Holz
 */
public class DemoPane extends BorderPane {
    private final PresentationModel pm;
    private SlimValueDisplay customControl;

    private TextField   titleField;
    private TextField   valueField;
    private TextField   unitField;
    private TextField   minValueField;
    private TextField   maxValueField;
    private ColorPicker colorPicker;


    public DemoPane(PresentationModel pm) {
        this.pm = pm;
        initializeControls();
        layoutControls();
        addBindings();
    }

    private void initializeControls() {
        setPadding(new Insets(10));

        customControl = new SlimValueDisplay();

        titleField    = new TextField();
        valueField    = new TextField();
        unitField     = new TextField();
        minValueField = new TextField();
        maxValueField = new TextField();

        colorPicker = new ColorPicker();
    }

    private void layoutControls() {
        VBox box = new VBox(titleField, valueField, unitField, new Label("min, max Value:"), minValueField, maxValueField, colorPicker);
        box.setSpacing(10);
        box.setPadding(new Insets(20));

        setCenter(customControl);
        setRight(box);
    }

    private void addBindings() {
        titleField.textProperty().bindBidirectional(pm.titleProperty());
        unitField.textProperty().bindBidirectional(pm.unitProperty());
        valueField.textProperty().bindBidirectional(pm.valueProperty(), new NumberStringConverter());
        minValueField.textProperty().bindBidirectional(pm.minProperty(), new NumberStringConverter());
        maxValueField.textProperty().bindBidirectional(pm.maxProperty(), new NumberStringConverter());
        colorPicker.valueProperty().bindBidirectional(pm.baseColorProperty());

        customControl.titleProperty().bindBidirectional(pm.titleProperty());
        customControl.unitProperty().bindBidirectional(pm.unitProperty());
        customControl.valueProperty().bindBidirectional(pm.valueProperty());
        customControl.minValueProperty().bindBidirectional(pm.minProperty());
        customControl.maxValueProperty().bindBidirectional(pm.maxProperty());
        customControl.baseColorProperty().bindBidirectional(pm.baseColorProperty());
    }

}
