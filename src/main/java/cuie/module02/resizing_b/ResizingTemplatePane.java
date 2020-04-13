package cuie.module02.resizing_b;

import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * @author Dieter Holz
 */
public class ResizingTemplatePane extends Region {
	private static final double ARTBOARD_SIZE = 300;
	private static final double MINIMUM_SIZE   = 75;
	private static final double MAXIMUM_SIZE   = 800;

	private static final Color  COLOR          = Color.rgb(2, 138, 204);
	private static final double BORDER_WIDTH   = 2.0;
	private static final double LINE_WIDTH     = 1.0;
	private static final double CENTER_RADIUS  = 5.0;

	private Rectangle border;
	private Line      line1;
	private Line      line2;
	private Circle    center;
	private Rectangle innerRect;

	private Pane drawingPane;

	public ResizingTemplatePane() {
        initializeSizes();
		initializeSelf();
		initializeControls();
		layoutControls();
	}

    private void initializeSizes() {
        Insets padding           = getPadding();
        double verticalPadding   = padding.getTop() + padding.getBottom();
        double horizontalPadding = padding.getLeft() + padding.getRight();
        setMinSize(MINIMUM_SIZE + horizontalPadding, MINIMUM_SIZE + verticalPadding);
        setPrefSize(ARTBOARD_SIZE + horizontalPadding, ARTBOARD_SIZE + verticalPadding);
        setMaxSize(MAXIMUM_SIZE + horizontalPadding, MAXIMUM_SIZE + verticalPadding);
    }

    private void initializeSelf() {
        String stylesheet = getClass().getResource("style.css").toExternalForm();
        getStylesheets().add(stylesheet);

        getStyleClass().add("resizing-template-pane");
    }

    //Initiale Parameter müssen gesetzt werden
	//1x richtig zeichnen
	private void initializeControls() {
		//Alle Elemente vollständig initialisieen:

		border = new Rectangle(0.0, 0.0, ARTBOARD_SIZE, ARTBOARD_SIZE);
		border.setFill(COLOR.deriveColor(0, 0.3, 1.5, 1));
		border.setStroke(COLOR);
		border.setStrokeWidth(BORDER_WIDTH);

		line1 = new Line(0.0, 0.0, ARTBOARD_SIZE, ARTBOARD_SIZE);
		line1.setStroke(COLOR);
		line1.setStrokeWidth(LINE_WIDTH);

		line2 = new Line(0.0, ARTBOARD_SIZE, ARTBOARD_SIZE, 0.0);
		line2.setStroke(COLOR);
		line2.setStrokeWidth(LINE_WIDTH);

		center = new Circle(ARTBOARD_SIZE * 0.5, ARTBOARD_SIZE * 0.5, CENTER_RADIUS);
		center.setFill(Color.TRANSPARENT);
		center.setStroke(COLOR);
		center.setStrokeWidth(LINE_WIDTH);

		innerRect = new Rectangle(ARTBOARD_SIZE * 0.25, ARTBOARD_SIZE * 0.25, ARTBOARD_SIZE * 0.5, ARTBOARD_SIZE * 0.5);
		innerRect.setFill(Color.TRANSPARENT);
		innerRect.setStroke(COLOR);
		innerRect.setStrokeWidth(LINE_WIDTH);

		drawingPane = new Pane();
		drawingPane.setMaxSize(ARTBOARD_SIZE, ARTBOARD_SIZE);
		drawingPane.setMinSize(ARTBOARD_SIZE, ARTBOARD_SIZE);
		drawingPane.setPrefSize(ARTBOARD_SIZE, ARTBOARD_SIZE);
	}

	private void layoutControls() {
		drawingPane.getChildren().addAll(border, line1, line2, center, innerRect);
		getChildren().add(drawingPane);
	}

	@Override
	protected void layoutChildren() {
		super.layoutChildren();
		resize();
	}

	//Gesamte DrawingPane wird skaliert (Alle Custom Controls die in DrawingPane sind werden mit skaliert)
	private void resize() {
		double width  = getWidth() - getInsets().getLeft() - getInsets().getRight();
		double height = getHeight() - getInsets().getTop() - getInsets().getBottom();
		double size = Math.max(Math.min(Math.min(width, height), MAXIMUM_SIZE), MINIMUM_SIZE);

		double scalingFactor = size / ARTBOARD_SIZE;

		if(width > 0 && height > 0){
			drawingPane.relocate((getWidth() - ARTBOARD_SIZE) * 0.5, (getHeight() - ARTBOARD_SIZE) * 0.5);
			drawingPane.setScaleX(scalingFactor);
			drawingPane.setScaleY(scalingFactor);
		}

		//Wenn sich beim Resizing das Höhen- zu Breitenverhältnis ändert, dann ist dies nicht anwendbar --> Variante a benutzen
	}
}
