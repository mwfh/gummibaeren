package cuie.module07.controllibrary_solution.demo;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

import cuie.module07.controllibrary_solution.NumberRangeControl;
import cuie.module07.controllibrary_solution.SkinType;

/**
 * @author Dieter Holz
 */
public class DemoPane extends BorderPane {
    private final PresentationModel pm;
    private NumberRangeControl customControl;

    private TextField           titleField;
    private TextField           valueField;
    private Label               valueLabel;
    private TextField           unitField;
    private TextField           minValueField;
    private TextField           maxValueField;
    private CheckBox            isAnimated;
    private CheckBox            isInteractive;
    private ColorPicker         baseColorPicker;
    private ColorPicker         outOfRangeColorPicker;
    private ChoiceBox<SkinType> skinTypeChoiceBox;

    public DemoPane(PresentationModel pm) {
        this.pm = pm;
        initializeControls();
        layoutControls();
        setupValueChangedListeners();
        setupBindings();
    }

    private void initializeControls() {
        customControl = new NumberRangeControl(pm.getSkinType());

        titleField            = new TextField();
        valueField            = new TextField();
        valueLabel            = new Label();
        unitField             = new TextField();
        minValueField         = new TextField();
        maxValueField         = new TextField();
        isAnimated            = new CheckBox();
        isInteractive         = new CheckBox();
        baseColorPicker       = new ColorPicker();
        outOfRangeColorPicker = new ColorPicker();
        skinTypeChoiceBox     = new ChoiceBox<>(FXCollections.observableArrayList(SkinType.values()));
    }

    private void layoutControls() {
        setPadding(new Insets(10));

        VBox box = new VBox(titleField, valueField, valueLabel, unitField,
                            new Label("min, max Value:")    , minValueField, maxValueField,
                            new Label("animated:")          , isAnimated,
                            new Label("interactive:")       , isInteractive,
                            new Label("base-color:")        , baseColorPicker,
                            new Label("out-of-range-color:"), outOfRangeColorPicker,
                            new Label("SkinType")           , skinTypeChoiceBox);
        box.setSpacing(10);
        box.setPadding(new Insets(20));

        setCenter(customControl);
        setRight(new ScrollPane(box));
    }

    private void setupValueChangedListeners(){
    }

    private void setupBindings() {
        NumberStringConverter converter = new NumberStringConverter(){
            @Override
            public Number fromString(String value) {
                try {
                    return super.fromString(value);
                } catch (Exception e) {
                    return 0;
                }
            }
        };

        titleField.textProperty().bindBidirectional(pm.titleProperty());
        unitField.textProperty().bindBidirectional(pm.unitProperty());
        valueField.textProperty().bindBidirectional(pm.valueProperty(), converter);
        valueLabel.textProperty().bind(pm.valueProperty().asString("%.2f"));
        minValueField.textProperty().bindBidirectional(pm.minProperty(), converter);
        maxValueField.textProperty().bindBidirectional(pm.maxProperty(), converter);
        isAnimated.selectedProperty().bindBidirectional(pm.animatedProperty());
        isInteractive.selectedProperty().bindBidirectional(pm.interactiveProperty());
        baseColorPicker.valueProperty().bindBidirectional(pm.baseColorProperty());
        outOfRangeColorPicker.valueProperty().bindBidirectional(pm.outOfRangeColorProperty());
        skinTypeChoiceBox.valueProperty().bindBidirectional(pm.skinTypeProperty());

        customControl.titleProperty().bindBidirectional(pm.titleProperty());
        customControl.unitProperty().bindBidirectional(pm.unitProperty());
        customControl.valueProperty().bindBidirectional(pm.valueProperty());
        customControl.minValueProperty().bindBidirectional(pm.minProperty());
        customControl.maxValueProperty().bindBidirectional(pm.maxProperty());
        customControl.animatedProperty().bindBidirectional(pm.animatedProperty());
        customControl.interactiveProperty().bindBidirectional(pm.interactiveProperty());
        customControl.originalBaseColorProperty().bindBidirectional(pm.baseColorProperty());
        customControl.originalOutOfRangeColorProperty().bindBidirectional(pm.outOfRangeColorProperty());
        customControl.skinTypeProperty().bindBidirectional(pm.skinTypeProperty());
    }

}
