package cuie.module07.controllibrary_solution.demo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

import cuie.module07.controllibrary_solution.SkinType;

/**
 * @author Dieter Holz
 */
public class PresentationModel {
    private final StringProperty           title           = new SimpleStringProperty("DIE ANTWORT");
    private final DoubleProperty           value           = new SimpleDoubleProperty(42);
    private final StringProperty           unit            = new SimpleStringProperty("UNIT");
    private final DoubleProperty           min             = new SimpleDoubleProperty(0);
    private final DoubleProperty           max             = new SimpleDoubleProperty(100);
    private final BooleanProperty          animated        = new SimpleBooleanProperty(true);
    private final BooleanProperty          interactive     = new SimpleBooleanProperty(false);
    private final ObjectProperty<Color>    baseColor       = new SimpleObjectProperty<>();
    private final ObjectProperty<Color>    outOfRangeColor = new SimpleObjectProperty<>();
    private final ObjectProperty<SkinType> skinType        = new SimpleObjectProperty<>(SkinType.FRIDAY_FUN);

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public double getValue() {
        return value.get();
    }

    public DoubleProperty valueProperty() {
        return value;
    }

    public void setValue(double value) {
        this.value.set(value);
    }

    public String getUnit() {
        return unit.get();
    }

    public StringProperty unitProperty() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }

    public double getMin() {
        return min.get();
    }

    public DoubleProperty minProperty() {
        return min;
    }

    public void setMin(double min) {
        this.min.set(min);
    }

    public double getMax() {
        return max.get();
    }

    public DoubleProperty maxProperty() {
        return max;
    }

    public void setMax(double max) {
        this.max.set(max);
    }

    public boolean isAnimated() {
        return animated.get();
    }

    public BooleanProperty animatedProperty() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated.set(animated);
    }

    public boolean isInteractive() {
        return interactive.get();
    }

    public BooleanProperty interactiveProperty() {
        return interactive;
    }

    public void setInteractive(boolean interactive) {
        this.interactive.set(interactive);
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

    public Color getOutOfRangeColor() {
        return outOfRangeColor.get();
    }

    public ObjectProperty<Color> outOfRangeColorProperty() {
        return outOfRangeColor;
    }

    public void setOutOfRangeColor(Color outOfRangeColor) {
        this.outOfRangeColor.set(outOfRangeColor);
    }

    public SkinType getSkinType() {
        return skinType.get();
    }

    public ObjectProperty<SkinType> skinTypeProperty() {
        return skinType;
    }

    public void setSkinType(SkinType skinType) {
        this.skinType.set(skinType);
    }
}
