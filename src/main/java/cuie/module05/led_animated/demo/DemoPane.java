package cuie.module05.led_animated.demo;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import cuie.module05.led_animated.LED;

/**
 * @author Dieter Holz
 */
public class DemoPane extends BorderPane {

    private final PresentationModel pm;
    // declare the custom control
    private       LED               led;

    // all controls
    private CheckBox onBox;
    private CheckBox blinkingBox;
    private Slider   blinkRateSlider;

    public DemoPane(PresentationModel pm) {
        this.pm = pm;
        initializeControls();
        layoutControls();
        setupBindings();
    }

    private void initializeControls() {
        setPadding(new Insets(10));

        led = new LED();

        onBox = new CheckBox("on");
        onBox.setSelected(true);

        blinkingBox = new CheckBox("animated");
        blinkingBox.setSelected(false);

        blinkRateSlider = new Slider(0.5, 2.0, 0);
        blinkRateSlider.setMinorTickCount(0);
        blinkRateSlider.setMajorTickUnit(0.5);
        blinkRateSlider.setShowTickMarks(true);
        blinkRateSlider.setShowTickLabels(true);
    }

    private void layoutControls() {
        setMargin(onBox, new Insets(0, 50, 10, 50));

        VBox controlPane = new VBox(new Label("LED animation"), onBox, blinkingBox, blinkRateSlider);
        controlPane.setPadding(new Insets(0, 50, 0, 50));
        controlPane.setSpacing(10);

        setCenter(led);
        setRight(controlPane);
    }

    private void setupBindings() {
        onBox.selectedProperty().bindBidirectional(pm.openProperty());
        blinkingBox.selectedProperty().bindBidirectional(pm.gateKeepingProperty());
        blinkRateSlider.valueProperty().bindBidirectional(pm.pulseProperty());
        led.blinkingProperty().bind(pm.gateKeepingProperty());

        led.onProperty().bindBidirectional(pm.openProperty());
    }

}
