package cuie.module07.controllibrary;

import java.util.List;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * @author Dieter Holz
 */
public class NumberRangeControl extends Control {
    // needed for StyleableProperties
    private static final StyleablePropertyFactory<NumberRangeControl> FACTORY = new StyleablePropertyFactory<>(Control.getClassCssMetaData());

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return FACTORY.getCssMetaData();
    }

    private static final Duration ANIMATION_DURATION = Duration.millis(400);

    // all properties
    private final StringProperty title    = new SimpleStringProperty("TITLE");
    private final StringProperty unit     = new SimpleStringProperty("UNIT");
    private final DoubleProperty minValue = new SimpleDoubleProperty(0);
    private final DoubleProperty maxValue = new SimpleDoubleProperty(1000);
    private final DoubleProperty value    = new SimpleDoubleProperty();

    private final BooleanProperty animated      = new SimpleBooleanProperty(true);
    private final DoubleProperty  animatedValue = new SimpleDoubleProperty();

    private final DoubleProperty  percentage = new SimpleDoubleProperty();
    private final DoubleProperty  angle      = new SimpleDoubleProperty();
    private final BooleanProperty outOfRange = new SimpleBooleanProperty();

    // all CSS Styleable properties
    private static final CssMetaData<NumberRangeControl, Color> BASE_COLOR_META_DATA = FACTORY.createColorCssMetaData("-base-color", s -> s.baseColor);

    private final StyleableObjectProperty<Color> baseColor = new SimpleStyleableObjectProperty<Color>(BASE_COLOR_META_DATA, this, "baseColor") {
        @Override
        protected void invalidated() {
            setStyle(BASE_COLOR_META_DATA.getProperty() + ": " + get().toString().replace("0x", "#"));
            applyCss();
        }
    };

    // animations
    private final Timeline timeline = new Timeline();

    private Color baseColorFromCSS;

    private final SkinType skinType;

    public NumberRangeControl(SkinType skinType) {
        this.skinType = skinType;
        getStyleClass().add("number-range-control");
        setupValueChangedListeners();
        setupBindings();
    }

    //SKIN WECHSELN:
    //Hier aussuchen welche Skin angezeigt werden soll:
    @Override
    protected Skin<?> createDefaultSkin() {

        if(skinType.equals(SkinType.LINEAR)){
            return new LinearSkin(this);
        }else if(skinType.equals(SkinType.PIE)){
            return new PieSkin(this);
        }else{
            return new SlimSkin(this);
        }

        //Alte Version: immer eins von beiden aussuchen und anderes auskommentiert lassen
        //return new SlimSkin(this);
        //return new LinearSkin(this);
    }

    private void setupValueChangedListeners() {
        valueProperty().addListener((observable, oldValue, newValue) -> {
            if (isAnimated()) {
                timeline.stop();
                timeline.getKeyFrames().setAll(new KeyFrame(ANIMATION_DURATION,
                                                            new KeyValue(animatedValueProperty(), newValue, Interpolator.EASE_BOTH)));

                timeline.play();
            } else {
                setAnimatedValue(newValue.doubleValue());
            }
        });

        outOfRangeProperty().addListener((observable, oldValue, newValue) -> {
            if (baseColorFromCSS == null) {
                baseColorFromCSS = getBaseColor();
            }
            if (newValue) {
                setBaseColor(Color.RED);  //Todo: Das sollte auch eine StyleableProperty sein!!
            } else {
                setBaseColor(baseColorFromCSS);
            }
        });
    }

    private void setupBindings() {
        percentage.bind(Bindings.createDoubleBinding(() -> valueToPercentage(getAnimatedValue(), getMinValue(), getMaxValue()),
                                                     animatedValue, minValue, maxValue));

        angle.bind(percentage.multiply(3.6));

        outOfRange.bind(Bindings.createBooleanBinding(() -> getValue() > getMaxValue() || getValue() < getMinValue(),
                                                      value, minValue, maxValue));
    }

    //some handy functions

    void loadFonts(String... font){
        for(String f : font){
            Font.loadFont(getClass().getResourceAsStream(f), 0);
        }
    }

    void addStylesheetFiles(String... stylesheetFile){
        for(String file : stylesheetFile){
            String stylesheet = getClass().getResource(file).toExternalForm();
            getStylesheets().add(stylesheet);
        }
    }

    private double percentageToValue(double percentage, double minValue, double maxValue) {
        assert percentage >= 0.0 && percentage <= 100.0;
        assert minValue <= maxValue;

        return ((maxValue - minValue) * (percentage / 100.0)) + minValue;
    }

    private double valueToPercentage(double value, double minValue, double maxValue) {
        assert minValue <= maxValue;

        return Math.max(0.0, Math.min(100.0, (value - minValue) / ((maxValue - minValue) / 100.0)));
    }

    private double angleToPercentage(double angle) {
        return angle / 3.6;
    }

    private double percentageToAngle(double percentage) {
        return 3.60 * percentage;
    }

    private double angle(double cx, double cy, double x, double y) {
        double deltaX = x - cx;
        double deltaY = y - cy;
        double radius = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
        double nx     = deltaX / radius;
        double ny     = deltaY / radius;
        double theta  = Math.toRadians(90) + Math.atan2(ny, nx);

        return Double.compare(theta, 0.0) >= 0 ? Math.toDegrees(theta) : Math.toDegrees((theta)) + 360.0;
    }

    // getter and setter for all properties

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
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

    public double getMinValue() {
        return minValue.get();
    }

    public DoubleProperty minValueProperty() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue.set(minValue);
    }

    public double getMaxValue() {
        return maxValue.get();
    }

    public DoubleProperty maxValueProperty() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue.set(maxValue);
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

    public boolean isAnimated() {
        return animated.get();
    }

    public BooleanProperty animatedProperty() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated.set(animated);
    }

    public double getAnimatedValue() {
        return animatedValue.get();
    }

    public DoubleProperty animatedValueProperty() {
        return animatedValue;
    }

    private void setAnimatedValue(double animatedValue) {
        this.animatedValue.set(animatedValue);
    }

    public double getPercentage() {
        return percentage.get();
    }

    public DoubleProperty percentageProperty() {
        return percentage;
    }

    public double getAngle() {
        return angle.get();
    }

    public DoubleProperty angleProperty() {
        return angle;
    }

    public boolean getOutOfRange() {
        return outOfRange.get();
    }

    public BooleanProperty outOfRangeProperty() {
        return outOfRange;
    }

    public Color getBaseColor() {
        return baseColor.get();
    }

    public StyleableObjectProperty<Color> baseColorProperty() {
        return baseColor;
    }

    public void setBaseColor(Color baseColor) {
        this.baseColor.set(baseColor);
    }

    private static String colorToCss(final Color color) {
        return color.toString().replace("0x", "#");
    }

}
