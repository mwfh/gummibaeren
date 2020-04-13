package cuie.module04.numberrange.demo;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import cuie.module04.numberrange.NumberRange;
import javafx.util.converter.NumberStringConverter;

/**
 * @author Dieter Holz
 */
public class DemoPane extends BorderPane {

    private final DemoPM rootPM;

    // declare the custom control
    private NumberRange range;

    // all controls
    private Label  valueLabel;
    private Slider slider;

    private Label minLabel;
    private Label maxLabel;
    private TextField minField;
    private TextField maxField;

    public DemoPane(DemoPM rootPM) {
        this.rootPM = rootPM;

        initializeControls();
        layoutControls();
        setupBindings();
    }

    private void initializeControls() {
        setPadding(new Insets(10));

        range = new NumberRange();

        valueLabel = new Label();
        slider = new Slider();
        slider.setShowTickLabels(true);

        minLabel = new Label("Min:");
        minField = new TextField();

        maxLabel = new Label("Max:");
        maxField = new TextField();
    }

    private void layoutControls() {
        VBox controlPane = new VBox(new Label("Range Properties"), new Label("Value"), valueLabel, slider, minLabel, minField, maxLabel, maxField);
        controlPane.setPadding(new Insets(0, 50, 0, 50));
        controlPane.setSpacing(10);

        setCenter(range);
        setRight(controlPane);
    }

    private void setupBindings() {
        // auf keinen Fall die beiden UI-Elemente direkt miteinander koppeln:
        // Das hier wäre FALSCH: valueLabel.textProperty().bind(slider.valueProperty().asString());

        //todo
        //Die PM-Properties und Range-Properties verbinden (da man vom range nicht auf den PM zugreifen kann)
        range.valueProperty().bindBidirectional(rootPM.temperatureProperty());
        range.minValueProperty().bind(rootPM.minValueProperty());
        range.maxValueProperty().bind(rootPM.maxValueProperty());

        // so isses richtig: UI-Elemente immer mit Properties aus dem PresentationModel verbinden
        valueLabel.textProperty().bind(rootPM.temperatureProperty().asString("%.1f"));
        slider.valueProperty().bindBidirectional(rootPM.temperatureProperty());

        slider.minProperty().bind(rootPM.minValueProperty());
        slider.maxProperty().bind(rootPM.maxValueProperty());
        minField.textProperty().bindBidirectional(rootPM.minValueProperty(), new NumberStringConverter());
        maxField.textProperty().bindBidirectional(rootPM.maxValueProperty(), new NumberStringConverter());


    }

}
