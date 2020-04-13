package cuie.module03.led_on_presentationmodel.demo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class DemoPM { //Gesamter Applikationszustand der verwaltet und visualisiert werden muss

    //PROPERTIES
    //Bin ich in einem gefährlichen Zustand - ja oder nein
    private BooleanProperty dangerous = new SimpleBooleanProperty();




    //GETTER & SETTER DER PROPERTIES
    public boolean isDangerous() {
        return dangerous.get();
    }

    public BooleanProperty dangerousProperty() {
        return dangerous;
    }

    public void setDangerous(boolean dangerous) {
        this.dangerous.set(dangerous);
    }
}
