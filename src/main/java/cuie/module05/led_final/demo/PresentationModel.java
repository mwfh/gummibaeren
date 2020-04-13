package cuie.module05.led_final.demo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * @author Dieter Holz
 */
public class PresentationModel {
    private BooleanProperty          on          = new SimpleBooleanProperty(true);
    private BooleanProperty          blinking    = new SimpleBooleanProperty(false);
    private ObjectProperty<Duration> circuitTime = new SimpleObjectProperty<>(Duration.millis(100));
    private ObjectProperty<Duration> pulse       = new SimpleObjectProperty<>(Duration.seconds(1.0));
    private ObjectProperty<Color>    color       = new SimpleObjectProperty<>(Color.RED);

    public boolean isOn() {
        return on.get();
    }

    public BooleanProperty onProperty() {
        return on;
    }

    public void setOn(boolean on) {
        this.on.set(on);
    }

    public boolean isBlinking() {
        return blinking.get();
    }

    public BooleanProperty blinkingProperty() {
        return blinking;
    }

    public void setBlinking(boolean blinking) {
        this.blinking.set(blinking);
    }

    public Duration getCircuitTime() {
        return circuitTime.get();
    }

    public ObjectProperty<Duration> circuitTimeProperty() {
        return circuitTime;
    }

    public void setCircuitTime(Duration circuitTime) {
        this.circuitTime.set(circuitTime);
    }

    public Duration getPulse() {
        return pulse.get();
    }

    public ObjectProperty<Duration> pulseProperty() {
        return pulse;
    }

    public void setPulse(Duration pulse) {
        this.pulse.set(pulse);
    }

    public Color getColor() {
        return color.get();
    }

    public ObjectProperty<Color> colorProperty() {
        return color;
    }

    public void setColor(Color color) {
        this.color.set(color);
    }
}
