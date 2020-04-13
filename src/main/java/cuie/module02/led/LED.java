package cuie.module02.led;

import javafx.geometry.Insets;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class LED extends Region {
    private static final double ARTBOARD_SIZE = 400;

    private static final double PREFERRED_SIZE = ARTBOARD_SIZE;
    private static final double MINIMUM_SIZE   = 14;
    private static final double MAXIMUM_SIZE   = 800;

    // Todo: ledColor als StyleableProperty realisieren und damit die Redundanz zum css-File eliminieren
    private static final Color       LED_COLOR    = Color.rgb(255, 0, 0);
    private static final InnerShadow INNER_SHADOW = new InnerShadow(BlurType.TWO_PASS_BOX, Color.rgb(0, 0, 0, 0.65), 8d * sizeFactor(), 0d, 0d, 0d);

    private static final DropShadow GLOW = new DropShadow(BlurType.THREE_PASS_BOX, LED_COLOR, 90d * (sizeFactor()), 0d, 0d, 0d);

    private Circle highlight;
    private Circle mainOn;
    private Circle mainOff;
    private Circle frame;

    private Pane drawingPane;

    {
        GLOW.setInput(INNER_SHADOW);
    }

    private static double sizeFactor() {
        return PREFERRED_SIZE / ARTBOARD_SIZE;
    }

    public LED() {
        initializeSelf();
        initializeParts();
        layoutParts();

        setOn(true);
    }

    private void initializeSelf() {
        String stylesheet = getClass().getResource("style.css").toExternalForm();
        getStylesheets().add(stylesheet);

        Insets padding           = getPadding();
        double verticalPadding   = padding.getTop() + padding.getBottom();
        double horizontalPadding = padding.getLeft() + padding.getRight();

        setMinSize(MINIMUM_SIZE + horizontalPadding, MINIMUM_SIZE + verticalPadding);
        setPrefSize(PREFERRED_SIZE + horizontalPadding, PREFERRED_SIZE + verticalPadding);
        setMaxSize(MAXIMUM_SIZE + horizontalPadding, MAXIMUM_SIZE + verticalPadding);
    }

    private void initializeParts() {
        double center = getPrefWidth() * 0.5;

        highlight = new Circle(center, center, 116 * sizeFactor());
        highlight.getStyleClass().addAll("highlight");

        mainOn = new Circle(center, center, 144 * sizeFactor());
        mainOn.getStyleClass().addAll("main-on");
        mainOn.setEffect(GLOW);

        mainOff = new Circle(center, center, 144 * sizeFactor());
        mainOff.getStyleClass().addAll("main-off");
        mainOff.setEffect(INNER_SHADOW);

        frame = new Circle(center, center, 200 * sizeFactor());
        frame.getStyleClass().addAll("frame");

        drawingPane = new Pane();
        drawingPane.setMaxSize(PREFERRED_SIZE, PREFERRED_SIZE);
        drawingPane.setMinSize(PREFERRED_SIZE, PREFERRED_SIZE);
        drawingPane.setPrefSize(PREFERRED_SIZE, PREFERRED_SIZE);
        drawingPane.getStyleClass().add("led");
    }

    public void setOn(boolean on) {
        mainOff.setVisible(!on);
        mainOn.setVisible(on);
    }

    private void layoutParts() {
        drawingPane.getChildren().addAll(frame, mainOff, mainOn, highlight);
        getChildren().add(drawingPane);
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
        double size            = Math.max(Math.min(Math.min(availableWidth, availableHeight), MAXIMUM_SIZE), MINIMUM_SIZE);

        double scalingFactor = size / PREFERRED_SIZE;

        if (availableWidth > 0 && availableHeight > 0) {
            drawingPane.relocate((getWidth() - PREFERRED_SIZE) * 0.5, (getHeight() - PREFERRED_SIZE) * 0.5);
            drawingPane.setScaleX(scalingFactor);
            drawingPane.setScaleY(scalingFactor);
        }
    }
}