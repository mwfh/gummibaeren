package cuie.module01.cssplaygrounds.path;

import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * @author Dieter Holz
 */
public class PathPane extends StackPane {
	private Region regionA;

	public PathPane() {
        initializeSelf();
		initializeParts();
		layoutParts();
	}

    private void initializeSelf() {
	    setPrefSize(300, 300);
        String stylesheet = getClass().getResource("style.css").toExternalForm();
        getStylesheets().add(stylesheet);

        getStyleClass().add("path-pane");
    }

	private void initializeParts() {
		regionA = new Region();
		regionA.getStyleClass().addAll("path-playground");
	}

	private void layoutParts() {
		getChildren().addAll(regionA);
	}
}
