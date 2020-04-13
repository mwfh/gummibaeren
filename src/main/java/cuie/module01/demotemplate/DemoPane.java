package cuie.module01.demotemplate;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

/**
 * @author Dieter Holz
 */
public class DemoPane extends StackPane {
    private Button button;

    public DemoPane() {
        initializeSelf();
        initializeParts();
        layoutParts();
    }

    private void initializeSelf() {
        //alle benoetigten Fonts
        Font.loadFont(getClass().getResourceAsStream("/fonts/ds_digital/DS-DIGI.TTF"), 0);
        //Wenn es mit / beginnt dann sucht er im Ressource-Ordner
        //Wenn kein / dann ab dem aktuellen Ordner

        String stylesheet = getClass().getResource("style.css").toExternalForm();
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
