package cuie.module03.led_and_slider.demo;

import javafx.beans.property.*;

/**
 * @author Dieter Holz
 */
public class DemoPM {
    //1. Properties
    private final StringProperty  demoTitle    = new SimpleStringProperty("LED using PresentationModel");
    private final IntegerProperty sliderValue = new SimpleIntegerProperty();//eingestellter Wert
    private final BooleanProperty inDangerZone = new SimpleBooleanProperty(true); //Ist der Wert gefährlich

    public DemoPM() {
    }

    //2. alle Getter und Setter von den Properties
    public int getSliderValue() {
        return sliderValue.get();
    }

    public IntegerProperty sliderValueProperty() {
        return sliderValue;
    }

    public void setSliderValue(int sliderValue) {
        this.sliderValue.set(sliderValue);
    }

    public String getDemoTitle() {
        return demoTitle.get();
    }

    public StringProperty demoTitleProperty() {
        return demoTitle;
    }

    public void setDemoTitle(String demoTitle) {
        this.demoTitle.set(demoTitle);
    }

    public boolean isInDangerZone() {
        return inDangerZone.get();
    }

    public BooleanProperty inDangerZoneProperty() {
        return inDangerZone;
    }

    public void setInDangerZone(boolean inDangerZone) {
        this.inDangerZone.set(inDangerZone);
    }

}
