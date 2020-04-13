package cuie.module07.controllibrary_solution;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author Dieter Holz
 */
class LinearSkin extends CustomControlSkinBase<NumberRangeControl> {
    private static final double ARTBOARD_WIDTH  = 400;
    private static final double ARTBOARD_HEIGHT = 24;

    private static final double MINIMUM_WIDTH  = ARTBOARD_HEIGHT * 2.5;
    private static final double MINIMUM_HEIGHT = 10;

    private static final double MAXIMUM_WIDTH  = 2048;
    private static final double MAXIMUM_HEIGHT = 100;

    private static final String FORMAT = "%.0f";

    // all parts
    private Text   value;
    private Circle thumb;
    private Line   valueBar;
    private Line   scale;

    private double originalBarStrokeWidth;
    private double originalThumbStrokeWidth;
    private String originalFontFamily;
    private double originalFontSize;

    private ChangeListener<Number> percentageListener;
    private ChangeListener<Number> animatedValueListener;

    LinearSkin(NumberRangeControl control) {
        super(control, ARTBOARD_WIDTH, ARTBOARD_HEIGHT, MAXIMUM_WIDTH, MINIMUM_WIDTH, MAXIMUM_HEIGHT, MINIMUM_HEIGHT);
    }

    @Override
    public void initializeSelf() {
        loadFonts("/fonts/Lato-Lig.ttf");
        addStylesheetFiles("linearStyle.css");
    }

    @Override
    public void initializeParts() {
        value = createCenteredText("value");
        value.setMouseTransparent(true);
        value.setText(String.format(FORMAT, getSkinnable().getValue()));

        thumb = new Circle();
        thumb.getStyleClass().add("thumb");

        valueBar = new Line();
        valueBar.getStyleClass().add("value-bar");
        valueBar.setStrokeLineCap(StrokeLineCap.ROUND);

        scale = new Line();
        scale.getStyleClass().add("scale");
        scale.setStrokeLineCap(StrokeLineCap.ROUND);

        Platform.runLater(() -> {
            value.applyCss();
            valueBar.applyCss();
            thumb.applyCss();
            originalFontSize = value.getFont().getSize();
            originalFontFamily = "Lato Light";
            originalThumbStrokeWidth = thumb.getStrokeWidth();
            originalBarStrokeWidth = valueBar.getStrokeWidth();
        });
    }

    @Override
    protected void addPartsToDrawingPane(Pane drawingPane) {
        drawingPane.getChildren().addAll(scale, valueBar, thumb, value);
    }

    @Override
    public void setupEventHandlers() {
        thumb.setOnMouseDragged(event -> {
            double percentage = 100.0 * ((event.getX() - scale.getStartX()) / (scale.getEndX() - scale.getStartX()));
            percentage = Math.min(100.0, Math.max(0, percentage));

            getSkinnable().setValueViaPercentage(percentage);
        });
    }

    @Override
    public void setupValueChangedListeners() {
        percentageListener = (observable, oldValue, newValue) -> {
            double xPos = getThumbXPos(newValue.doubleValue());
            thumb.setCenterX(xPos);
            valueBar.setEndX(xPos);
        };

        animatedValueListener = (observable, oldValue, newValue) -> value.setText(String.format(FORMAT, newValue.doubleValue()));

        getSkinnable().percentageProperty().addListener(percentageListener);
        getSkinnable().animatedValueProperty().addListener(animatedValueListener);
    }

    @Override
    protected void resize() {
        Insets padding         = getSkinnable().getPadding();
        double availableWidth  = getSkinnable().getWidth() - padding.getLeft() - padding.getRight();
        double availableHeight = getSkinnable().getHeight() - padding.getTop() - padding.getBottom();

        double actualHeight   = Math.max(Math.min(availableHeight, MAXIMUM_HEIGHT), MINIMUM_HEIGHT);
        double scalingFactorY = actualHeight / ARTBOARD_HEIGHT;
        double actualWidth    = Math.max(Math.min(availableWidth, MAXIMUM_WIDTH), MINIMUM_WIDTH * scalingFactorY);

        if (availableWidth > 0 && availableHeight > 0) {
            getDrawingPane().setMaxSize(actualWidth, actualHeight);
            getDrawingPane().setMinSize(actualWidth, actualHeight);
            getDrawingPane().relocate((getSkinnable().getWidth() - actualWidth) * 0.5, (getSkinnable().getHeight() - actualHeight) * 0.5);

            double margin            = actualHeight * 0.5;
            double scaledStrokeWidth = originalBarStrokeWidth * scalingFactorY;
            double thumbX            = margin + (actualWidth - 2 * margin) / 100.0 * getSkinnable().getPercentage();

            scale.setStartX(margin);
            scale.setStartY(margin);
            scale.setEndX(actualWidth - margin);
            scale.setEndY(margin);
            scale.setStrokeWidth(scaledStrokeWidth);

            valueBar.setStartX(margin);
            valueBar.setStartY(margin);
            valueBar.setEndY(margin);
            valueBar.setEndX(thumbX);
            valueBar.setStrokeWidth(scaledStrokeWidth);

            thumb.setCenterX(thumbX);
            thumb.setCenterY(margin);
            thumb.setRadius(actualHeight * 0.5);
            thumb.setStrokeWidth(originalThumbStrokeWidth * scalingFactorY);

            value.setFont(Font.font(originalFontFamily, (int)(originalFontSize * scalingFactorY)));
            value.setX(thumbX - value.getWrappingWidth() * 0.5);
            value.setY(margin);
        }
    }

    @Override
    public void dispose() {
        getSkinnable().percentageProperty().removeListener(percentageListener);
        getSkinnable().animatedValueProperty().removeListener(animatedValueListener);

        super.dispose();
    }

    private double getThumbXPos(double percentage) {
        return scale.getStartX() + percentage / 100.0 * (scale.getEndX() - scale.getStartX()) - thumb.getRadius();
    }

}
