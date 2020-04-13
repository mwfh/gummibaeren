package cuie.module07.controllibrary_solution;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

/**
 * @author Dieter Holz
 */
class FridayFunSkin extends CustomControlSkinBase<NumberRangeControl> {
    private static final double ARTBOARD_SIZE = 300;
    private static final double MINIMUM_SIZE  = 75;
    private static final double MAXIMUM_SIZE  = 800;

    //declare all parts
    private Circle     backgroundCircle;
    private Arc        bar;
    private Circle     thumb;
    private Text       valueDisplay;
    private Group      ticks;
    private List<Text> tickLabels;

    private ChangeListener<Number> angleListener;
    private ChangeListener<Number> minMaxListener;

    FridayFunSkin(NumberRangeControl control) {
        super(control, ARTBOARD_SIZE, ARTBOARD_SIZE, MAXIMUM_SIZE, MINIMUM_SIZE, MAXIMUM_SIZE, MINIMUM_SIZE);

        relocateThumb();
    }

    @Override
    protected void initializeSelf() {
        loadFonts("/fonts/Lato-Lig.ttf");
        addStylesheetFiles("fridayFunStyle.css");

        angleListener  = (observable, oldValue, newValue) -> relocateThumb();
        minMaxListener = (observable, oldValue, newValue) -> updateTickLabels();
    }

    @Override
    protected void initializeParts() {
        double center = ARTBOARD_SIZE * 0.5;
        int    width  = 15;
        double radius = center - width;

        backgroundCircle = new Circle(center, center, radius);
        backgroundCircle.getStyleClass().add("background-circle");

        bar = new Arc(center, center, radius, radius, 90.0, 0.0);
        bar.getStyleClass().add("bar");
        bar.setType(ArcType.OPEN);

        thumb = new Circle(center, center + center - width, 13);
        thumb.getStyleClass().add("thumb");

        valueDisplay = createCenteredText(center, center, "value-display");

        ticks = createTicks(center, center, radius - width - 3, 60, 0.0, 360.0, 6, "tick");

        tickLabels = new ArrayList<>();

        int labelCount = 8;
        for (int i = 0; i < labelCount; i++) {
            double r     = 95;
            double nextAngle = i * 360.0 / labelCount;

            Point2D p         = pointOnCircle(center, center, r, nextAngle);
            Text    tickLabel = createCenteredText(p.getX(), p.getY(), "tick-label");

            tickLabels.add(tickLabel);
        }
        updateTickLabels();
    }

    @Override
    protected void addPartsToDrawingPane(Pane drawingPane) {
        drawingPane.getChildren().addAll(backgroundCircle, bar, thumb, valueDisplay, ticks);
        drawingPane.getChildren().addAll(tickLabels);
    }

    @Override
    protected void setupEventHandlers() {
        thumb.setOnMouseDragged(event -> {
            double center = ARTBOARD_SIZE * 0.5;
            getSkinnable().setAnimated(false);
            getSkinnable().setAngle(center, center, event.getX(), event.getY());
            getSkinnable().setAnimated(true);
        });
    }

    @Override
    protected void setupValueChangedListeners() {
        getSkinnable().angleProperty().addListener(angleListener);
        getSkinnable().minValueProperty().addListener(minMaxListener);
        getSkinnable().maxValueProperty().addListener(minMaxListener);
    }

    @Override
    protected void setupBindings() {
        valueDisplay.textProperty().bind(getSkinnable().valueProperty().asString("%.1f"));
        bar.lengthProperty().bind(getSkinnable().angleProperty().multiply(-1.0));
    }

    @Override
    public void dispose() {
        getSkinnable().angleProperty().removeListener(angleListener);
        getSkinnable().minValueProperty().removeListener(minMaxListener);
        getSkinnable().maxValueProperty().removeListener(minMaxListener);

        valueDisplay.textProperty().unbind();
        bar.lengthProperty().unbind();

        thumb.setOnMouseDragged(null);

        super.dispose();
    }

    private void updateTickLabels() {
        int labelCount = tickLabels.size();
        double step    = (getSkinnable().getMaxValue() - getSkinnable().getMinValue()) / labelCount;

        for (int i = 0; i < labelCount; i++) {
            Text tickLabel = tickLabels.get(i);
            tickLabel.setText(String.format("%.1f", getSkinnable().getMinValue() + (i * step)));
        }
    }

    private void relocateThumb(){
        double  center = ARTBOARD_SIZE * 0.5;
        Point2D thumbCenter = pointOnCircle(center, center, center - 15, getSkinnable().getAngle());
        thumb.setCenterX(thumbCenter.getX());
        thumb.setCenterY(thumbCenter.getY());
    }

}
