package cuie.module04.template.demo;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Dieter Holz
 */
public class DemoPM {
    // all the properties waiting for being displayed
    // ToDo: ersetzen durch die Properties, die via CustomControl visualisiert oder modifiziert werden sollen
    private final StringProperty demoTitle = new SimpleStringProperty("Custom Control Demo");
    private final DoubleProperty someValue = new SimpleDoubleProperty();


    // all getters and setters (generated via "Code -> Generate -> Getter and Setter)

    public String getDemoTitle() {
        return demoTitle.get();
    }

    public StringProperty demoTitleProperty() {
        return demoTitle;
    }

    public void setDemoTitle(String demoTitle) {
        this.demoTitle.set(demoTitle);
    }

    public double getSomeValue() {
        return someValue.get();
    }

    public DoubleProperty someValueProperty() {
        return someValue;
    }

    public void setSomeValue(double someValue) {
        this.someValue.set(someValue);
    }
}
