package cuie.module08.timecontrol_manufactory;

import javafx.scene.layout.VBox;


public class DropDownChooser extends VBox {
    private static final String STYLE_CSS = "dropDownChooser.css";

    private final MyTimeControl timeControl;

    // todo: add all your controls here




    public DropDownChooser(MyTimeControl timeControl) {
        this.timeControl = timeControl;
        initializeSelf();
        initializeParts();
        layoutParts();
        setupBindings();
    }

    private void initializeSelf() {
        String stylesheet = getClass().getResource(STYLE_CSS).toExternalForm();
        getStylesheets().add(stylesheet);

        getStyleClass().add("dropdown-chooser");
    }

    private void initializeParts() {
    }

    private void layoutParts() {
        getChildren().addAll();
    }

    private void setupBindings() {
    }
}
