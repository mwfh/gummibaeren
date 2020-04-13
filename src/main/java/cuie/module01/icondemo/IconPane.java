package cuie.module01.icondemo;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

/**
 * @author Dieter Holz
 */
public class IconPane extends StackPane {
	private static final String SAVE   = "\uf0c7"; //Hier UNICODE des Icons auf fontawsome eigeben
	private static final String BETT   = "\uf236"; //Hier UNICODE des Icons auf fontawsome eigeben

	private Button button;

	public IconPane() {
        initializeSelf();
		initializeParts();
		layoutParts();
	}

    private void initializeSelf() {
        Font.loadFont(getClass().getResourceAsStream("/fonts/fontawesome-webfont.ttf"), 0); //Spezial-Font laden

        String stylesheet = getClass().getResource("style.css").toExternalForm();
        getStylesheets().add(stylesheet);

        getStyleClass().add("icon-pane");
    }

	private void initializeParts() {
		button = new Button(BETT); //Hier die Font verwenden
		button.getStyleClass().add("icon");
	}

	private void layoutParts() {
		getChildren().add(button);
	}
}
