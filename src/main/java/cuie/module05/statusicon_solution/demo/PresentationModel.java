package cuie.module05.statusicon_solution.demo;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import cuie.module05.statusicon_solution.StatusIcon;

/**
 * @author Dieter Holz
 */
public class PresentationModel {
    private ObjectProperty<StatusIcon.Status> currentStatus = new SimpleObjectProperty<>(StatusIcon.Status.OPEN_RIGHT);

    public StatusIcon.Status getCurrentStatus() {
        return currentStatus.get();
    }

    public ObjectProperty<StatusIcon.Status> currentStatusProperty() {
        return currentStatus;
    }

    public void setCurrentStatus(StatusIcon.Status currentStatus) {
        this.currentStatus.set(currentStatus);
    }
}
