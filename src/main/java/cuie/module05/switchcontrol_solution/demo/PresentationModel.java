package cuie.module05.switchcontrol_solution.demo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * @author Dieter Holz
 */
public class PresentationModel {
    private final BooleanProperty on = new SimpleBooleanProperty(true);

    public boolean isOn() {
        return on.get();
    }

    public BooleanProperty onProperty() {
        return on;
    }

    public void setOn(boolean on) {
        this.on.set(on);
    }
}
