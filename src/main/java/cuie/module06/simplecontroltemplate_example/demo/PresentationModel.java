package cuie.module06.simplecontroltemplate_example.demo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

/**
 * @author Dieter Holz
 */
public class PresentationModel {
    private final StringProperty        message   = new SimpleStringProperty("Wow!");
    private final DoubleProperty        score     = new SimpleDoubleProperty(90);
    private final BooleanProperty       alarm     = new SimpleBooleanProperty(false);
    private final DoubleProperty        pulse     = new SimpleDoubleProperty(1.0);
    private final ObjectProperty<Color> baseColor = new SimpleObjectProperty<>();

    public String getMessage() {
        return message.get();
    }

    public StringProperty messageProperty() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    public double getScore() {
        return score.get();
    }

    public DoubleProperty scoreProperty() {
        return score;
    }

    public void setScore(double score) {
        this.score.set(score);
    }

    public boolean isAlarm() {
        return alarm.get();
    }

    public BooleanProperty alarmProperty() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm.set(alarm);
    }

    public double getPulse() {
        return pulse.get();
    }

    public DoubleProperty pulseProperty() {
        return pulse;
    }

    public void setPulse(double pulse) {
        this.pulse.set(pulse);
    }

    public Color getBaseColor() {
        return baseColor.get();
    }

    public ObjectProperty<Color> baseColorProperty() {
        return baseColor;
    }

    public void setBaseColor(Color baseColor) {
        this.baseColor.set(baseColor);
    }
}
