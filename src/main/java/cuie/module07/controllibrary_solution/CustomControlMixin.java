package cuie.module07.controllibrary_solution;

import javafx.scene.control.Control;
import javafx.scene.text.Font;

/**
 * @author Dieter Holz
 */
public interface CustomControlMixin<C extends Control> {
    default void init() {
        initializeSelf();
        initializeParts();
        initializeDrawingPane();
        layoutParts();
        setupEventHandlers();
        setupValueChangedListeners();
        setupBindings();
    }

    default void initializeSelf() {
    }

    void initializeParts();

    void initializeDrawingPane();

    void layoutParts();

    default void setupEventHandlers() {
    }

    default void setupValueChangedListeners() {
    }

    default void setupBindings() {
    }

    default void loadFonts(String... font){
        for(String f : font){
            Font.loadFont(getClass().getResourceAsStream(f), 0);
        }
    }

    default void addStylesheetFiles(String... stylesheetFile) {
        for (String file : stylesheetFile) {
            String stylesheet = getClass().getResource(file).toExternalForm();
            getSkinnable().getStylesheets().add(stylesheet);
        }
    }

    C getSkinnable();
}
