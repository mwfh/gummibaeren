package cuie.module05.led_animated.demo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * @author Dieter Holz
 */
public class PresentationModel {
    private final BooleanProperty open        = new SimpleBooleanProperty(true);
    private final BooleanProperty gateKeeping = new SimpleBooleanProperty(false);
    private final DoubleProperty  pulse       = new SimpleDoubleProperty(1.0);

    public boolean getOpen() {
        return open.get();
    }

    public BooleanProperty openProperty() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open.set(open);
    }

    public boolean getGateKeeping() {
        return gateKeeping.get();
    }

    public BooleanProperty gateKeepingProperty() {
        return gateKeeping;
    }

    public void setGateKeeping(boolean gateKeeping) {
        this.gateKeeping.set(gateKeeping);
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
}
