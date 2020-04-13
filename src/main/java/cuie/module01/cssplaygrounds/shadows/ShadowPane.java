package cuie.module01.cssplaygrounds.shadows;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * @author Dieter Holz
 */
public class ShadowPane extends HBox {
	private Region regionA;
	private Region regionB;

	public ShadowPane() {
        initializeSelf();
		initializeParts();
		layoutParts();
	}

    private void initializeSelf() {
        String stylesheet = getClass().getResource("style.css").toExternalForm();
        getStylesheets().add(stylesheet);

        getStyleClass().add("shadow-pane");
    }

	private void initializeParts() {
		regionA = new Region();
		regionA.getStyleClass().addAll("playground", "drop-shadow-playground");

		regionB = new Region();
		regionB.getStyleClass().addAll("playground", "inner-shadow-playground");
	}

	private void layoutParts() {
		setHgrow(regionA, Priority.ALWAYS);
		setHgrow(regionB, Priority.ALWAYS);
		getChildren().addAll(regionA, regionB);
	}
}
