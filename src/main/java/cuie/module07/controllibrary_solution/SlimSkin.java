package cuie.module07.controllibrary_solution;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

/**
 * @author Dieter Holz
 */
class SlimSkin extends CustomControlSkinBase<NumberRangeControl>{
    private static final double ARTBOARD_WIDTH  = 250;
    private static final double ARTBOARD_HEIGHT = 265;

    private static final double ASPECT_RATIO = ARTBOARD_WIDTH / ARTBOARD_HEIGHT;

    private static final double MINIMUM_WIDTH  = 80;
    private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;

    private static final double MAXIMUM_WIDTH  = 800;
    private static final double MAXIMUM_HEIGHT = MAXIMUM_WIDTH / ASPECT_RATIO;

    private static final String FORMAT     = "%.1f";
    private static final long   BLINK_RATE = 500_000_000L;

    private static final int CIRCLE_CENTER_X = 125;
    private static final int CIRCLE_CENTER_Y = 150;
    private static final int RADIUS          = 100;
    private static final int START_ANGLE     = 90;

    // all parts
    private Line   separator;
    private Text   titleLabel;
    private Text   valueLabel;
    private Text   unitLabel;
    private Circle barBackground;
    private Arc    bar;
    private Circle thumb;

     // animations
    private AnimationTimer timer = new AnimationTimer() {
        private long lastTimerCall;

        @Override
        public void handle(long now) {
            if (now > lastTimerCall + BLINK_RATE) {
                performPeriodicTask();
                lastTimerCall = now;
            }
        }
    };

    private ChangeListener<Boolean> outOfRangeListener;
    private ChangeListener<Number>  angleListener;

    SlimSkin(NumberRangeControl control) {
        super(control, ARTBOARD_WIDTH, ARTBOARD_HEIGHT, MAXIMUM_WIDTH, MINIMUM_WIDTH, MAXIMUM_HEIGHT, MINIMUM_HEIGHT);

        relocateThumb();
    }

    @Override
    public void initializeSelf() {
        loadFonts("/fonts/Lato-Lig.ttf", "/fonts/Lato-Reg.ttf");
        addStylesheetFiles("slimStyle.css");

        outOfRangeListener = (observable, oldValue, newValue) -> {
                if (newValue) {
                    timer.start();
                } else {
                    timer.stop();
                    bar.setVisible(true);
                    separator.setVisible(true);
                }
            };

        angleListener = (observable, oldValue, newValue) -> relocateThumb();
    }

    @Override
    public void initializeParts() {
        double cx = ARTBOARD_WIDTH * 0.5;

        separator = new Line(25, 15, 225, 15);
        separator.getStyleClass().add("separator");
        separator.setStrokeLineCap(StrokeLineCap.ROUND);

        titleLabel = createCenteredText(cx, 19, "title");
        titleLabel.setTextOrigin(VPos.TOP);

        valueLabel = createCenteredText(cx, 150, "value");

        unitLabel = createCenteredText(cx, 188, "unit");
        unitLabel.setTextOrigin(VPos.TOP);

        barBackground = new Circle(CIRCLE_CENTER_X, CIRCLE_CENTER_Y, RADIUS);
        barBackground.getStyleClass().add("bar-background");

        bar = new Arc(CIRCLE_CENTER_X, CIRCLE_CENTER_Y, RADIUS, RADIUS, START_ANGLE, 0);
        bar.getStyleClass().add("bar");
        bar.setType(ArcType.OPEN);

        thumb = new Circle(7);
        thumb.getStyleClass().add("thumb");
    }

    @Override
    protected void addPartsToDrawingPane(Pane drawingPane) {
        drawingPane.getChildren().addAll(barBackground, bar, separator, titleLabel, valueLabel, unitLabel, thumb);
    }

    @Override
    protected void setupEventHandlers() {
        thumb.setOnMouseDragged(event -> {
            boolean animated = getSkinnable().isAnimated();
            getSkinnable().setAnimated(false);
            getSkinnable().setAngle(CIRCLE_CENTER_X, CIRCLE_CENTER_Y, event.getX(), event.getY());
            getSkinnable().setAnimated(animated);
        });
    }

    @Override
    protected void setupValueChangedListeners() {
        getSkinnable().outOfRangeProperty().addListener(outOfRangeListener);
        getSkinnable().angleProperty().addListener(angleListener);
    }

    @Override
    protected void setupBindings() {
        titleLabel.textProperty().bind(getSkinnable().titleProperty());
        unitLabel.textProperty().bind(getSkinnable().unitProperty());
        valueLabel.textProperty().bind(getSkinnable().animatedValueProperty().asString(FORMAT));
        bar.lengthProperty().bind(Bindings.min(getSkinnable().angleProperty().multiply(-1.0), -1.0));
        thumb.visibleProperty().bind(getSkinnable().interactiveProperty());
    }

    @Override
    public void dispose() {
        getSkinnable().outOfRangeProperty().removeListener(outOfRangeListener);
        getSkinnable().angleProperty().removeListener(angleListener);

        titleLabel.textProperty().unbind();
        unitLabel.textProperty().unbind();
        valueLabel.textProperty().unbind();
        bar.lengthProperty().unbind();
        thumb.visibleProperty().unbind();

        super.dispose();
    }

    private void performPeriodicTask() {
        bar.setVisible(!bar.isVisible());
        separator.setVisible(!separator.isVisible());
    }

    private void relocateThumb() {
        Point2D p = pointOnCircle(CIRCLE_CENTER_X, CIRCLE_CENTER_Y, RADIUS, getSkinnable().getAngle());

        thumb.setCenterX(p.getX());
        thumb.setCenterY(p.getY());
    }

}
