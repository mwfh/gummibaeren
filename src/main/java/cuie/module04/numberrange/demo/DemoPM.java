package cuie.module04.numberrange.demo;

import javafx.beans.property.*;

/**
 * @author Dieter Holz
 */
public class DemoPM {
    private final StringProperty demoTitle   = new SimpleStringProperty("NumberRange Demo");
    private final DoubleProperty temperature = new SimpleDoubleProperty(42);

    private final IntegerProperty minValue = new SimpleIntegerProperty();
    private final IntegerProperty maxValue = new SimpleIntegerProperty();

    public String getDemoTitle() {
        return demoTitle.get();
    }

    public StringProperty demoTitleProperty() {
        return demoTitle;
    }

    public void setDemoTitle(String demoTitle) {
        this.demoTitle.set(demoTitle);
    }

    public double getTemperature() {
        return temperature.get();
    }

    public DoubleProperty temperatureProperty() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature.set(temperature);
    }

    public int getMinValue() {
        return minValue.get();
    }

    public IntegerProperty minValueProperty() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue.set(minValue);
    }

    public int getMaxValue() {
        return maxValue.get();
    }

    public IntegerProperty maxValueProperty() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue.set(maxValue);
    }
}
