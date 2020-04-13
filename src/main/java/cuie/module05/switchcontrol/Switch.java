package cuie.module05.switchcontrol;

import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * @author Dieter Holz
 */
public class Switch extends Region {
    //Todo : Redundant zu style.css. Einführen von StyledProperties wird erst später behandelt.
    private static final Color THUMB_ON  = Color.rgb( 62, 130, 247);
    private static final Color THUMB_OFF = Color.rgb(250, 250, 250);
    private static final Color FRAME_ON  = Color.rgb(162, 197, 255);
    private static final Color FRAME_OFF = Color.rgb(153, 153, 153);

    private static final double ARTBOARD_WIDTH  = 40;
    private static final double ARTBOARD_HEIGHT = 26;

    private static final double ASPECT_RATIO = ARTBOARD_WIDTH / ARTBOARD_HEIGHT;

    private static final double MINIMUM_WIDTH  = 20;
    private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;

    private static final double MAXIMUM_WIDTH = 160;

    // all parts
    private Circle    thumb;
    private Rectangle frame;

    // all properties
    private final BooleanProperty on = new SimpleBooleanProperty();

    // all animations
    private Transition onTransition;
    private Transition offTransition;

    private Pane drawingPane;

    public Switch() {
        initializeSelf();
        initializeParts();
        initializeDrawingPane();
        initializeAnimations();
        layoutParts();
        setupEventHandlers();
        setupValueChangeListeners();
        setupBindings();
    }

    private void initializeSelf() {
        //loadFonts("/fonts/Lato/Lato-Lig.ttf");
        addStylesheetFiles("style.css");

        getStyleClass().add("switch");
    }

    private void initializeParts() {
        thumb = new Circle(12, 13, 10);
        thumb.getStyleClass().add("thumb");
        thumb.setStrokeWidth(0);
        thumb.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.3), 4, 0, 0, 1));
        thumb.setMouseTransparent(true);

        frame = new Rectangle(2.0, 6.0, ARTBOARD_WIDTH - 4.0, ARTBOARD_HEIGHT - 12);
        frame.getStyleClass().add("frame");
        frame.setMouseTransparent(true);
    }

    private void initializeDrawingPane(){
        drawingPane = new Pane();
        drawingPane.setMaxSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setMinSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setPrefSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);

        drawingPane.setCursor(Cursor.HAND);
    }

    private void initializeAnimations(){
        //onTransition
        TranslateTransition onTranslation = new TranslateTransition(Duration.millis(500),thumb);
        onTranslation.setFromX(0);
        onTranslation.setToX(16);

        FillTransition onFill = new FillTransition(Duration.millis(500), frame);
        onFill.setFromValue(FRAME_OFF);
        onFill.setToValue(FRAME_ON);

        onTransition = new ParallelTransition(onTranslation, onFill);

        //offTransition
        TranslateTransition offTranslation = new TranslateTransition(Duration.millis(500), thumb);
        offTranslation.setFromX(16);
        offTranslation.setToX(0);

        FillTransition offFill = new FillTransition(Duration.millis(500), frame);
        offFill.setFromValue(FRAME_ON);
        offFill.setToValue(FRAME_OFF);

        offTransition = new ParallelTransition(offTranslation);
    }

    private void layoutParts() {
        drawingPane.getChildren().addAll(frame, thumb);

        getChildren().add(drawingPane);
    }

    private void setupEventHandlers() {
        drawingPane.setOnMouseClicked(event -> setOn(!isOn()));
    }

    private void setupValueChangeListeners() {
        onProperty().addListener((observable, oldValue, newValue) -> updateUI()); //wert beobachten und updateUI aufrufen
    }

    private void setupBindings() {
    }

    private void updateUI(){ //UpdateUI definieren
        onTransition.stop();
        offTransition.stop();

        if(isOn()){
            //thumb.setLayoutX(16);
            onTransition.play();
        }
        else {
            //thumb.setLayoutX(0);
            offTransition.play();
        }
       // thumb.setLayoutX(isOn() ? 16 : 0);
    }

    //resize by scaling
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
