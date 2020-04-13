package cuie.module01.demotemplate_withoutfonts;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * @author Dieter Holz
 */
public class DemoPane extends StackPane {

    // Deklaration aller Controls
    private Button button;

    public DemoPane() {
        initializeSelf();
        initializeParts();
        layoutParts();
    }

    private void initializeSelf() {
        String stylesheet = getClass().getResource("style.css").toExternalForm(); // CSS einbinden (muss in gl. Package liegen)
        getStylesheets().add(stylesheet);

        getStyleClass().add("root-pane");
    }

    private void initializeParts() {
        button = new Button("Hello World");
    }

    private void layoutParts() {
        getChildren().add(button);
    }
}
