package cuie.module01.cssplaygrounds.bordersandbackgrounds;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * @author Dieter Holz
 */
public class BordersAndBackgroundPane extends HBox {
	private Region regionA;
	private Region regionB;
	private Region regionC;

	public BordersAndBackgroundPane() {
        initializeSelf();
		initializeParts();
		layoutParts();
	}

    private void initializeSelf() {
        String stylesheet = getClass().getResource("style.css").toExternalForm();
        getStylesheets().add(stylesheet);
        getStyleClass().add("borders-and-background-pane");
    }

	private void initializeParts() {
		regionA = new Region();
		regionA.getStyleClass().addAll("playground", "background-playground");

		regionB = new Region();
		regionB.getStyleClass().addAll("playground", "border-playground");

		regionC = new Region();
		regionC.getStyleClass().addAll("playground", "border-and-background-playground");
	}

	private void layoutParts() {
		setHgrow(regionA, Priority.ALWAYS);
		setHgrow(regionB, Priority.ALWAYS);
		getChildren().addAll(regionA, regionB, regionC);
	}
}
