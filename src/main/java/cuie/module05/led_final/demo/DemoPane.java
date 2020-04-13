package cuie.module05.led_final.demo;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import cuie.module05.led_final.LedFinal;

/**
 * @author Dieter Holz
 */
public class DemoPane extends BorderPane {
    private final PresentationModel pm;

    private LedFinal customControl;

    private CheckBox    onBox;
    private Slider      circuitTimeSlider;
    private CheckBox    timerRunningBox;
    private Slider      pulseSlider;
    private ColorPicker colorPicker;

    public DemoPane(PresentationModel pm) {
        this.pm = pm;
        initializeControls();
        layoutControls();
        addBindings();
    }

    private void initializeControls() {
        setPadding(new Insets(10));

        customControl = new LedFinal();

        onBox = new CheckBox("On");

        circuitTimeSlider = new Slider(100, 2000, 100);
        circuitTimeSlider.setShowTickLabels(true);

        timerRunningBox = new CheckBox("Blinking");
        timerRunningBox.setSelected(false);

        pulseSlider = new Slider(0.5, 2.0, 1.0);
        pulseSlider.setShowTickLabels(true);
        pulseSlider.setShowTickMarks(true);

        colorPicker = new ColorPicker();
    }

    private void layoutControls() {
        setCenter(customControl);
        VBox box = new VBox(10, new Label("Control Properties"), onBox,
                            new Label("Einschaltzeit"), circuitTimeSlider,
                            timerRunningBox,
                            new Label("Pulse"), pulseSlider,
                            colorPicker);
        box.setPadding(new Insets(10));
        box.setSpacing(10);
        setRight(box);
    }

    private void addBindings() {
        onBox.selectedProperty().bindBidirectional(pm.onProperty());
        pm.circuitTimeProperty().bind(Bindings.createObjectBinding(() -> Duration.millis(circuitTimeSlider.getValue()), circuitTimeSlider.valueProperty()));
        timerRunningBox.selectedProperty().bindBidirectional(pm.blinkingProperty());
        pm.pulseProperty().bind(Bindings.createObjectBinding(() -> Duration.seconds(pulseSlider.getValue()), pulseSlider.valueProperty()));
        colorPicker.valueProperty().bindBidirectional(pm.colorProperty());

        customControl.onProperty().bindBidirectional(pm.onProperty());
        customControl.circuitTimeProperty().bind(pm.circuitTimeProperty());
        customControl.timerIsRunningProperty().bindBidirectional(pm.blinkingProperty());
        customControl.pulseProperty().bind(pm.pulseProperty());
        customControl.baseColorProperty().bind(pm.colorProperty());
    }

}
