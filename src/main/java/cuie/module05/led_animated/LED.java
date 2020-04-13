package cuie.module05.led_animated;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class LED extends Region {
    private static final double ARTBOARD_SIZE = 400;

    private static final double MINIMUM_SIZE = 14;
    private static final double MAXIMUM_SIZE = 800;

    // Todo: ledColor als StyleableProperty realisieren und damit die Redundanz zum css-File eliminieren
    private static final Color       LED_COLOR    = Color.rgb(255, 0, 0);
    private static final InnerShadow INNER_SHADOW = new InnerShadow(BlurType.TWO_PASS_BOX, Color.rgb(0, 0, 0, 0.65), 8d, 0d, 0d, 0d);

    private static final DropShadow GLOW = new DropShadow(BlurType.THREE_PASS_BOX, LED_COLOR, 90d, 0d, 0d, 0d);

    // all visual parts
    private Circle highlight;
    private Circle mainOn;
    private Circle mainOff;
    private Circle frame;

    // all properties
    private final BooleanProperty on = new SimpleBooleanProperty(true);
    private final BooleanProperty blinking = new SimpleBooleanProperty();

    // drawing pane needed for resizing
    private Pane drawingPane;

    static {
        GLOW.setInput(INNER_SHADOW);
    }

    private AnimationTimer timer = new AnimationTimer() {
            private long lastTimerCall;

            @Override
            public void handle(long now){
                if(now > lastTimerCall + (0.5 * 1_000_000_000L)){
                    setOn(!isOn());
                    lastTimerCall = now;
            }
        }
    };

    public LED() {
        initializeSelf();
        initializeParts();
        initializeDrawingPane();
        layoutParts();
        setupEventHandlers();
        setupValueChangeListeners();

        //timer.start();
    }

    private void initializeSelf() {
        loadFonts("/fonts/Lato/Lato-Lig.ttf");
        addStylesheetFiles("style.css");

        getStyleClass().add("led");
    }

    private void initializeParts() {
        double center = ARTBOARD_SIZE * 0.5;

        highlight = new Circle(center, center, 116);
        highlight.getStyleClass().addAll("highlight");

        mainOn = new Circle(center, center, 144);
        mainOn.getStyleClass().addAll("main-on");
        mainOn.setEffect(GLOW);

        mainOff = new Circle(center, center, 144);
        mainOff.getStyleClass().addAll("main-off");
        mainOff.setEffect(INNER_SHADOW);

        frame = new Circle(center, center, 200);
        frame.getStyleClass().addAll("frame");
    }

    private void initializeDrawingPane() {
        drawingPane = new Pane();
        drawingPane.setMaxSize(ARTBOARD_SIZE, ARTBOARD_SIZE);
        drawingPane.setMinSize(ARTBOARD_SIZE, ARTBOARD_SIZE);
        drawingPane.setPrefSize(ARTBOARD_SIZE, ARTBOARD_SIZE);
        drawingPane.getStyleClass().add("drawing-pane");
    }

    private void layoutParts() {
        drawingPane.getChildren().addAll(frame, mainOff, mainOn, highlight);

        getChildren().add(drawingPane);
    }

    private void setupEventHandlers() {
        drawingPane.setOnMouseClicked(event -> setOn(!isOn()));
    }

    private void setupValueChangeListeners() {
        onProperty().addListener((observable, oldValue, newValue) -> updateUI());

        blinkingProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                timer.start();
            }else{
                timer.stop();
            }
        });
    }

    private void updateUI() {
        mainOn.setVisible(isOn());
        mainOff.setVisible(!isOn());
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        resize();
    }

    private void resize() {
        Insets padding         = getPadding();
        double availableWidth  = getWidth() - padding.getLeft() - padding.getRight();
        double availableHeight = getHeight() - padding.getTop() - padding.getBottom();

        double width = Math.max(Math.min(Math.min(availableWidth, availableHeight), MAXIMUM_SIZE), MINIMUM_SIZE);

        double scalingFactor = width / ARTBOARD_SIZE;

        if (availableWidth > 0 && availableHeight > 0) {
            drawingPane.relocate((getWidth() - ARTBOARD_SIZE) * 0.5, (getHeight() - ARTBOARD_SIZE) * 0.5);
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

        return MINIMUM_SIZE + horizontalPadding;
    }

    @Override
    protected double computeMinHeight(double width) {
        Insets padding         = getPadding();
        double verticalPadding = padding.getTop() + padding.getBottom();

        return MINIMUM_SIZE + verticalPadding;
    }

    @Override
    protected double computePrefWidth(double height) {
        Insets padding           = getPadding();
        double horizontalPadding = padding.getLeft() + padding.getRight();

        return ARTBOARD_SIZE + horizontalPadding;
    }

    @Override
    protected double computePrefHeight(double width) {
        Insets padding         = getPadding();
        double verticalPadding = padding.getTop() + padding.getBottom();

        return ARTBOARD_SIZE + verticalPadding;
    }

    // alle getter und setter

    public boolean isOn() {
        return on.get();
    }

    public BooleanProperty onProperty() {
        return on;
    }

    public void setOn(boolean on) {
        this.on.set(on);
    }

    public boolean isBlinking() {
        return blinking.get();
    }

    public BooleanProperty blinkingProperty() {
        return blinking;
    }

    public void setBlinking(boolean blinking) {
        this.blinking.set(blinking);
    }
}