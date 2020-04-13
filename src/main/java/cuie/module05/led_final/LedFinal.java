package cuie.module05.led_final;

import java.util.List;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.geometry.Insets;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * @author Dieter Holz
 */
public class LedFinal extends Region {
    // needed for StyleableProperties
    private static final StyleablePropertyFactory<LedFinal> FACTORY = new StyleablePropertyFactory<>(Region.getClassCssMetaData());

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return FACTORY.getCssMetaData();
    }

    private static final double ARTBOARD_WIDTH  = 400;
    private static final double ARTBOARD_HEIGHT = 400;

    private static final double ASPECT_RATIO = ARTBOARD_WIDTH / ARTBOARD_HEIGHT;

    private static final double MINIMUM_WIDTH  = 14;
    private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;

    private static final double MAXIMUM_WIDTH = 800;

    private InnerShadow innerShadow;
    private DropShadow  glow;

    // all parts
    private Circle highlight;
    private Circle mainOn;
    private Circle mainOff;
    private Circle frame;

    // all properties
    private final BooleanProperty          on          = new SimpleBooleanProperty(true);
    private final ObjectProperty<Duration> circuitTime = new SimpleObjectProperty<>(Duration.millis(100));

    private final BooleanProperty          timerIsRunning = new SimpleBooleanProperty(false);
    private final ObjectProperty<Duration> pulse          = new SimpleObjectProperty<>(Duration.seconds(1.0));

    //CSS stylable properties
    private static final CssMetaData<LedFinal, Color> BASE_COLOR_META_DATA = FACTORY.createColorCssMetaData("-base-color", s -> s.baseColor);

    private final StyleableObjectProperty<Color> baseColor = new SimpleStyleableObjectProperty<Color>(BASE_COLOR_META_DATA, this, "baseColor") {
        @Override
        protected void invalidated() {
            setStyle(BASE_COLOR_META_DATA.getProperty() + ": " + get().toString().replace("0x", "#")  + ";");
            applyCss();
        }
    };

    // all animations
    private Animation onTransition;
    private Animation offTransition;

    // all parts need to be children of the drawingPane
    private Pane drawingPane;

    private final AnimationTimer timer = new AnimationTimer() {
        private long lastTimerCall;

        @Override
        public void handle(long now) {
            if (now > lastTimerCall + (getPulse().toMillis() * 1_000_000L)) {
                performPeriodicTask();
                lastTimerCall = now;
            }
        }
    };

    public LedFinal() {
        initializeSelf();
        initializeParts();
        initializeDrawingPane();
        layoutParts();
        initializeAnimations();
        setupEventHandlers();
        setupValueChangedListeners();
        setupBindings();
    }

    private void initializeSelf() {
        loadFonts("/fonts/Lato/Lato-Lig.ttf");
        addStylesheetFiles("style.css");

        getStyleClass().add("led-final");

        innerShadow = new InnerShadow(BlurType.TWO_PASS_BOX, Color.rgb(0, 0, 0, 0.65), 8d, 0d, 0d, 0d);
        glow = new DropShadow(BlurType.THREE_PASS_BOX, getBaseColor(), 90d, 0d, 0d, 0d);
        glow.setInput(innerShadow);
        glow.colorProperty().bind(baseColorProperty());
    }

    private void initializeParts() {
        double center = ARTBOARD_WIDTH * 0.5;

        highlight = new Circle(center, center, 116);
        highlight.getStyleClass().addAll("highlight");

        mainOn = new Circle(center, center, 144);
        mainOn.getStyleClass().addAll("main-on");
        mainOn.setEffect(glow);

        mainOff = new Circle(center, center, 144);
        mainOff.getStyleClass().addAll("main-off");
        mainOff.setEffect(innerShadow);

        frame = new Circle(center, center, 200);
        frame.getStyleClass().addAll("frame");
    }

    private void initializeDrawingPane() {
        drawingPane = new Pane();
        drawingPane.setMaxSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setMinSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setPrefSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.getStyleClass().add("drawing-pane");
    }

    private void layoutParts() {
        // add all your parts here
        drawingPane.getChildren().addAll(frame, mainOff, mainOn, highlight);

        getChildren().add(drawingPane);
    }

    private void initializeAnimations() {
        FadeTransition mainFadeIn = new FadeTransition(getCircuitTime(), mainOn);
        mainFadeIn.setFromValue(0.0);
        mainFadeIn.setToValue(1.0);

        FadeTransition mainOffFadeOut = new FadeTransition(getCircuitTime(), mainOff);
        mainOffFadeOut.setFromValue(1.0);
        mainOffFadeOut.setToValue(0.0);

        onTransition = new ParallelTransition(mainFadeIn, mainOffFadeOut);

        FadeTransition mainFadeOut = new FadeTransition(getCircuitTime(), mainOn);
        mainFadeOut.setFromValue(1.0);
        mainFadeOut.setToValue(0.0);

        FadeTransition mainOffFadeIn = new FadeTransition(getCircuitTime(), mainOff);
        mainOffFadeIn.setFromValue(0.0);
        mainOffFadeIn.setToValue(1.0);

        offTransition = new ParallelTransition(mainFadeOut, mainOffFadeIn);
    }

    private void setupEventHandlers() {
        mainOff.setMouseTransparent(true);
        mainOn.setMouseTransparent(true);
        highlight.setMouseTransparent(true);
        frame.setOnMouseClicked(event -> setOn(!isOn()));
    }

    private void setupValueChangedListeners() {
        onProperty().addListener((observable, oldValue, newValue) -> {
            onTransition.stop();
            offTransition.stop();
            if (newValue) {
                onTransition.play();
            } else {
                offTransition.play();
            }
        });

        circuitTimeProperty().addListener((observable, oldValue, newValue) -> initializeAnimations());

        // if you need the timer
        timerIsRunningProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                timer.start();
            } else {
                timer.stop();
            }
        });
    }

    private void setupBindings() {
    }

    private void performPeriodicTask() {
        setOn(!isOn());
    }

    // some useful helper-methods

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        resize();
    }

    private void resize() {
        Insets padding         = getPadding();
        double availableWidth  = getWidth() - padding.getLeft() - padding.getRight();
        double availableHeight = getHeight() - padding.getTop() - padding.getBottom();

        double width = Math.max(Math.min(Math.min(availableWidth, availableHeight * ASPECT_RATIO), MAXIMUM_WIDTH), MINIMUM_WIDTH);

        double scalingFactor = width / ARTBOARD_WIDTH;

        if (availableWidth > 0 && availableHeight > 0) {
            drawingPane.relocate((getWidth() - ARTBOARD_WIDTH) * 0.5, (getHeight() - ARTBOARD_HEIGHT) * 0.5);
            drawingPane.setScaleX(scalingFactor);
            drawingPane.setScaleY(scalingFactor);
        }
    }

    private void loadFonts(String... font){
        for(String f : font){
            Font.loadFont(getClass().getResourceAsStream(f), 0);
        }
    }

    private void addStylesheetFiles(String... stylesheetFile){
        for(String file : stylesheetFile){
            String stylesheet = getClass().getResource(file).toExternalForm();
            getStylesheets().add(stylesheet);
        }
    }

    // compute sizes

    @Override
    protected double computeMinWidth(double height) {
        Insets padding           = getPadding();
        double horizontalPadding = padding.getLeft() + padding.getRight();

        return MINIMUM_WIDTH + horizontalPadding;
    }

    @Override
    protected double computeMinHeight(double width) {
        Insets padding         = getPadding();
        double verticalPadding = padding.getTop() + padding.getBottom();

        return MINIMUM_HEIGHT + verticalPadding;
    }

    @Override
    protected double computePrefWidth(double height) {
        Insets padding           = getPadding();
        double horizontalPadding = padding.getLeft() + padding.getRight();

        return ARTBOARD_WIDTH + horizontalPadding;
    }

    @Override
    protected double computePrefHeight(double width) {
        Insets padding         = getPadding();
        double verticalPadding = padding.getTop() + padding.getBottom();

        return ARTBOARD_HEIGHT + verticalPadding;
    }

    // getter and setter for all properties

    public Color getBaseColor() {
        return baseColor.get();
    }

    public StyleableObjectProperty<Color> baseColorProperty() {
        return baseColor;
    }

    public void setBaseColor(Color baseColor) {
        this.baseColor.set(baseColor);
    }

    public boolean isTimerIsRunning() {
        return timerIsRunning.get();
    }

    public BooleanProperty timerIsRunningProperty() {
        return timerIsRunning;
    }

    public void setTimerIsRunning(boolean timerIsRunning) {
        this.timerIsRunning.set(timerIsRunning);
    }

    public Duration getPulse() {
        return pulse.get();
    }

    public ObjectProperty<Duration> pulseProperty() {
        return pulse;
    }

    public void setPulse(Duration pulse) {
        this.pulse.set(pulse);
    }

    public boolean isOn() {
        return on.get();
    }

    public BooleanProperty onProperty() {
        return on;
    }

    public void setOn(boolean on) {
        this.on.set(on);
    }

    public Duration getCircuitTime() {
        return circuitTime.get();
    }

    public ObjectProperty<Duration> circuitTimeProperty() {
        return circuitTime;
    }

    public void setCircuitTime(Duration circuitTime) {
        this.circuitTime.set(circuitTime);
    }
}
