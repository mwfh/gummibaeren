package cuie.module07.controllibrary_solution;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Control;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

/**
 * @author Dieter Holz
 */
public abstract class CustomControlSkinBase<C extends Control> extends SkinBase<C> {

    private final double artboardWidth;
    private final double artboardHeight;
    private final double maxWidth;
    private final double minWidth;
    private final double maxHeight;
    private final double minHeight;

    // needed for resizing
    private Pane drawingPane;

    public CustomControlSkinBase(C control, double artboardWidth, double artboardHeight, double maxWidth, double minWidth, double maxHeight, double minHeight) {
        super(control);
        this.artboardWidth = artboardWidth;
        this.artboardHeight = artboardHeight;
        this.maxWidth = maxWidth;
        this.minWidth = minWidth;
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;

        initializeSelf();
        initializeParts();
        initializeDrawingPane();
        layoutParts();
        setupEventHandlers();
        setupValueChangedListeners();
        setupBindings();
    }


    protected abstract void initializeSelf();

    protected abstract void initializeParts();

    protected void initializeDrawingPane() {
        drawingPane = new Pane();
        drawingPane.getStyleClass().add("drawing-pane");
        drawingPane.setMaxSize(artboardWidth,  artboardHeight);
        drawingPane.setMinSize(artboardWidth,  artboardHeight);
        drawingPane.setPrefSize(artboardWidth, artboardHeight);
    }

    private void layoutParts() {
        addPartsToDrawingPane(drawingPane);

        getChildren().add(drawingPane);
    }

    protected abstract void addPartsToDrawingPane(Pane drawingPane);

    protected void setupEventHandlers() {
    }

    protected void setupValueChangedListeners() {
    }

    protected void setupBindings() {
    }

    @Override
   	protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
   		super.layoutChildren(contentX, contentY, contentWidth, contentHeight);
   		resize();
   	}

    protected void resize() {
        Insets padding         = getSkinnable().getPadding();
        double availableWidth  = getSkinnable().getWidth() - padding.getLeft() - padding.getRight();
        double availableHeight = getSkinnable().getHeight() - padding.getTop() - padding.getBottom();

        double width = Math.max(Math.min(Math.min(availableWidth, availableHeight * (artboardWidth/artboardHeight)), maxWidth), minWidth);

        double scalingFactor = width / artboardWidth;

        if (availableWidth > 0 && availableHeight > 0) {
            //ToDo: ueberpruefen ob die drawingPane immer zentriert werden soll (eventuell ist zum Beispiel linksbuendig angemessener)
            relocateDrawingPaneCentered();
            drawingPane.setScaleX(scalingFactor);
            drawingPane.setScaleY(scalingFactor);
        }
    }

    private void relocateDrawingPaneCentered() {
        drawingPane.relocate((getSkinnable().getWidth() - artboardWidth) * 0.5, (getSkinnable().getHeight() - artboardHeight) * 0.5);
    }

    protected Pane getDrawingPane() {
        return drawingPane;
    }

    protected void loadFonts(String... font){
        for(String f : font){
            Font.loadFont(getClass().getResourceAsStream(f), 0);
        }
    }

    protected void addStylesheetFiles(String... stylesheetFile) {
        for (String file : stylesheetFile) {
            String stylesheet = getClass().getResource(file).toExternalForm();
            getSkinnable().getStylesheets().add(stylesheet);
        }
    }


    protected Point2D pointOnCircle(double cX, double cY, double radius, double angle) {
        return new Point2D(cX - (radius * Math.sin(Math.toRadians(angle - 180))),
                           cY + (radius * Math.cos(Math.toRadians(angle - 180))));
    }

    protected Text createCenteredText(String styleClass) {
        return createCenteredText(artboardWidth * 0.5, artboardHeight * 0.5, styleClass);
    }

    protected Text createCenteredText(double cx, double cy, String styleClass) {
        Text text = new Text();
        text.getStyleClass().add(styleClass);
        text.setTextOrigin(VPos.CENTER);
        text.setTextAlignment(TextAlignment.CENTER);
        double width = cx > artboardWidth * 0.5 ? ((artboardWidth - cx) * 2.0) : cx * 2.0;
        text.setWrappingWidth(width);
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setY(cy);
        text.setX(cx - (width / 2.0));

        return text;
    }

    protected Group createTicks(double cx, double cy, double radius, int numberOfTicks, double startingAngle, double overallAngle, double tickLength, String styleClass) {
        Group group = new Group();

        double degreesBetweenTicks = overallAngle == 360 ?
                                     overallAngle / numberOfTicks :
                                     overallAngle /(numberOfTicks - 1);
        double innerRadius         = radius - tickLength;

        for (int i = 0; i < numberOfTicks; i++) {
            double angle = startingAngle + i * degreesBetweenTicks;

            Point2D startPoint = pointOnCircle(cx, cy, radius,      angle);
            Point2D endPoint   = pointOnCircle(cx, cy, innerRadius, angle);

            Line tick = new Line(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
            tick.getStyleClass().add(styleClass);
            group.getChildren().add(tick);
        }

        return group;
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        double horizontalPadding = leftInset + rightInset;

        return minWidth + horizontalPadding;
    }

    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        double verticalPadding = topInset + bottomInset;

        return  minHeight + verticalPadding;
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        double horizontalPadding = leftInset + rightInset;

        return artboardWidth + horizontalPadding;
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        double verticalPadding = topInset + bottomInset;

        return artboardHeight + verticalPadding;
    }

    @Override
    protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        double horizontalPadding = leftInset + rightInset;

        return maxWidth + horizontalPadding;
    }

    @Override
    protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        double verticalPadding = topInset + bottomInset;

        return maxHeight + verticalPadding;
    }


}
