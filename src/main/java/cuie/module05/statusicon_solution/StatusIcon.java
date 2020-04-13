package cuie.module05.statusicon_solution;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * @author Dieter Holz
 */
public class StatusIcon extends Region {
    public enum Status {

        CLOSED_HORIZONZAL(new Point2D(25, 40), new Point2D(75, 40),
                          new Point2D(25, 50), new Point2D(75, 50),
                          new Point2D(25, 60), new Point2D(75, 60)),

        CLOSED_VERTICAL  (new Point2D(40, 35), new Point2D(40, 65),
                          new Point2D(50, 35), new Point2D(50, 65),
                          new Point2D(60, 35), new Point2D(60, 65)),

        OPEN_LEFT        (new Point2D(25, 50), new Point2D(45, 40),
                          new Point2D(25, 50), new Point2D(75, 50),
                          new Point2D(25, 50), new Point2D(45, 60)),

        OPEN_RIGHT       (new Point2D(55, 40), new Point2D(75, 50),
                          new Point2D(25, 50), new Point2D(75, 50),
                          new Point2D(55, 60), new Point2D(75, 50));

        private final Point2D line1Start;
        private final Point2D line2Start;
        private final Point2D line3Start;

        private final Point2D line1End;
        private final Point2D line2End;
        private final Point2D line3End;

        Status(Point2D line1Start, Point2D line1End, Point2D line2Start, Point2D line2End, Point2D line3Start, Point2D line3End) {
            this.line1Start = line1Start;
            this.line1End   = line1End;
            this.line2Start = line2Start;
            this.line2End   = line2End;
            this.line3Start = line3Start;
            this.line3End   = line3End;
        }

        public Point2D getLine1Start() {
            return line1Start;
        }

        public Point2D getLine2Start() {
            return line2Start;
        }

        public Point2D getLine3Start() {
            return line3Start;
        }

        public Point2D getLine1End() {
            return line1End;
        }

        public Point2D getLine2End() {
            return line2End;
        }

        public Point2D getLine3End() {
            return line3End;
        }
    }

    private static final double ARTBOARD_WIDTH  = 100;
    private static final double ARTBOARD_HEIGHT = 100;

    private static final double ASPECT_RATIO = ARTBOARD_WIDTH / ARTBOARD_HEIGHT;

    private static final double MINIMUM_WIDTH  = 30;
    private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;

    private static final double MAXIMUM_WIDTH = 800;

    // all parts
    private Line line1;
    private Line line2;
    private Line line3;

    private Circle frame;

    private Pane drawingPane;

    // all properties
    private final ObjectProperty<Status> status = new SimpleObjectProperty<>();

    // all timelines
    private final Timeline timeline = new Timeline();

    public StatusIcon() {
        initializeSelf();
        initializeParts();
        initializeDrawingPane();
        layoutParts();
        setupEventHandlers();
        setupValueChangedListeners();
        setupBindings();
    }

    private void initializeSelf() {
        //loadFonts("/fonts/Lato/Lato-Lig.ttf");
        addStylesheetFiles("style.css");

        getStyleClass().add("status-icon");
    }

    private void initializeDrawingPane() {
        drawingPane = new Pane();
        drawingPane.getStyleClass().add("drawing-pane");
        drawingPane.setMaxSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setMinSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setPrefSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
    }

    private void initializeParts() {
        line1 = new Line();
        line1.getStyleClass().add("line");
        line1.setMouseTransparent(true);

        line2 = new Line();
        line2.getStyleClass().add("line");
        line2.setMouseTransparent(true);

        line3 = new Line();
        line3.getStyleClass().add("line");
        line3.setMouseTransparent(true);

        frame = new Circle(50, 50, 50);
        frame.getStyleClass().add("frame");
    }

    private void layoutParts() {
        drawingPane.getChildren().addAll(frame, line1, line2, line3);
        getChildren().add(drawingPane);
    }

    private void setupEventHandlers() {
        frame.setOnMouseClicked(event -> {
            int currentOrdinal = getStatus().ordinal();
            int nextOrdinal    = (currentOrdinal == Status.values().length - 1) ? 0 : currentOrdinal + 1;
            setStatus(Status.values()[nextOrdinal]);
        });
    }

    private void setupValueChangedListeners() {
        statusProperty().addListener((observable, oldValue, newValue) -> updateUI());
    }

    private void setupBindings() {
    }

    private void updateUI() {
        if (timeline.getStatus().equals(Animation.Status.RUNNING)) {
            return;
        }

        Status status = getStatus();
        KeyFrame kf = new KeyFrame(Duration.millis(200),
                                   new KeyValue(line1.startXProperty(), status.getLine1Start().getX()),
                                   new KeyValue(line1.startYProperty(), status.getLine1Start().getY()),
                                   new KeyValue(line1.endXProperty(),   status.getLine1End().getX()),
                                   new KeyValue(line1.endYProperty(),   status.getLine1End().getY()),

                                   new KeyValue(line2.startXProperty(), status.getLine2Start().getX()),
                                   new KeyValue(line2.startYProperty(), status.getLine2Start().getY()),
                                   new KeyValue(line2.endXProperty(),   status.getLine2End().getX()),
                                   new KeyValue(line2.endYProperty(),   status.getLine2End().getY()),

                                   new KeyValue(line3.startXProperty(), status.getLine3Start().getX()),
                                   new KeyValue(line3.startYProperty(), status.getLine3Start().getY()),
                                   new KeyValue(line3.endXProperty(),   status.getLine3End().getX()),
                                   new KeyValue(line3.endYProperty(),   status.getLine3End().getY())
        );

        timeline.getKeyFrames().setAll(kf);
        timeline.play();
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

        double width = Math.max(Math.min(Math.min(availableWidth, availableHeight * ASPECT_RATIO), MAXIMUM_WIDTH), MINIMUM_WIDTH);

        double scalingFactor = width / ARTBOARD_WIDTH;

        if (availableWidth > 0 && availableHeight > 0) {
            drawingPane.relocate((getWidth() - ARTBOARD_WIDTH) * 0.5, (getHeight() - ARTBOARD_HEIGHT) * 0.5);
            drawingPane.setScaleX(scalingFactor);
            drawingPane.setScaleY(scalingFactor);
        }
    }

    // some useful helper-methods


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

    public Status getStatus() {
        return status.get();
    }

    public ObjectProperty<Status> statusProperty() {
        return status;
    }

    public void setStatus(Status status) {
        this.status.set(status);
    }
}
