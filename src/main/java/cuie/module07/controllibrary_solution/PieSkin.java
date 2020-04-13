package cuie.module07.controllibrary_solution;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.util.converter.NumberStringConverter;

/**
 * @author Dieter Holz
 */
class PieSkin extends CustomControlSkinBase<NumberRangeControl>{
    private static final double ARTBOARD_WIDTH  = 130;
	private static final double ARTBOARD_HEIGHT = 30;

	private static final double ASPECT_RATIO = ARTBOARD_WIDTH / ARTBOARD_HEIGHT;

	private static final double MINIMUM_WIDTH  = 20;
	private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;

	private static final double MAXIMUM_WIDTH  = 800;
	private static final double MAXIMUM_HEIGHT = MAXIMUM_WIDTH / ASPECT_RATIO;

	// all parts
	private Circle    border;
	private Arc       pieSlice;
	private TextField valueField;

	PieSkin(NumberRangeControl control) {
		super(control, ARTBOARD_WIDTH, ARTBOARD_HEIGHT, MAXIMUM_WIDTH, MINIMUM_WIDTH, MAXIMUM_HEIGHT, MINIMUM_HEIGHT);
	}

    @Override
	public void initializeSelf() {
	    loadFonts("/fonts/Lato-Lig.ttf");
	    addStylesheetFiles("pieStyle.css");
	}

    @Override
    public void initializeParts() {
		double center = ARTBOARD_HEIGHT * 0.5;
		border = new Circle(center, center, center);
		border.getStyleClass().add("border");

		pieSlice = new Arc(center, center, center - 1, center - 1, 90, 0);
		pieSlice.getStyleClass().add("pie-slice");
		pieSlice.setType(ArcType.ROUND);

		valueField = new TextField();
		valueField.relocate(ARTBOARD_HEIGHT + 5, 2);
		valueField.setPrefWidth(ARTBOARD_WIDTH - ARTBOARD_HEIGHT - 5);
		valueField.getStyleClass().add("value-field");
	}

    @Override
    protected void addPartsToDrawingPane(Pane drawingPane) {
        drawingPane.getChildren().addAll(border, pieSlice, valueField);
    }

    @Override
	public void setupBindings() {
		pieSlice.lengthProperty().bind(Bindings.min(getSkinnable().angleProperty().multiply(-1.0), 0.0));
        valueField.textProperty().bindBidirectional(getSkinnable().valueProperty(), new NumberStringConverter());
    }

    @Override
    public void dispose() {
	    pieSlice.lengthProperty().unbind();
	    valueField.textProperty().unbindBidirectional(getSkinnable().valueProperty());

        super.dispose();
    }
}
