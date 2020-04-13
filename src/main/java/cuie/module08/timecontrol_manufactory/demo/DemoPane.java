package cuie.module08.timecontrol_manufactory.demo;

import java.time.LocalTime;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import cuie.module08.timecontrol_manufactory.MyTimeControl;
import cuie.module08.timecontrol_manufactory.SkinType;


public class DemoPane extends BorderPane {
    private final PresentationModel pm;

    private MyTimeControl businessControl;

    private Label  timeLabel;
    private Slider hourSlider;
    private Slider minuteSlider;

    private CheckBox  readOnlyBox;
    private CheckBox  mandatoryBox;
    private TextField labelField;

    public DemoPane(PresentationModel pm) {
        this.pm = pm;
        initializeControls();
        layoutControls();
        setupValueChangeListeners();
        setupBindings();
    }

    private void initializeControls() {
        setPadding(new Insets(10));

        businessControl = new MyTimeControl(SkinType.EXPERIMENTAL);

        timeLabel    = new Label();
        hourSlider   = new Slider(0, 23, 0);
        minuteSlider = new Slider(0, 59, 0);
        readOnlyBox  = new CheckBox();
        mandatoryBox = new CheckBox();
        labelField   = new TextField();
    }

    private void layoutControls() {
        setCenter(businessControl);
        VBox box = new VBox(10, new Label("Time Control Properties"),
                            timeLabel, hourSlider, minuteSlider,
                            new Label("readOnly"), readOnlyBox,
                            new Label("mandatory"), mandatoryBox,
                            new Label("Label"), labelField);
        box.setPadding(new Insets(10));
        box.setSpacing(10);
        setRight(box);
    }

    private void setupValueChangeListeners() {
        ChangeListener<Number> sliderListener = (observable, oldValue, newValue) ->
                pm.setStartTime(LocalTime.of((int) hourSlider.getValue(), (int) minuteSlider.getValue()));

        hourSlider.valueProperty().addListener(sliderListener);
        minuteSlider.valueProperty().addListener(sliderListener);

        pm.startTimeProperty().addListener((observable, oldValue, newValue) -> updateSliders());

        updateSliders();
    }

    private void updateSliders() {
        LocalTime startTime = pm.getStartTime();

        hourSlider.setValue(startTime.getHour());
        minuteSlider.setValue(startTime.getMinute());
    }

    private void setupBindings() {
        timeLabel.textProperty().bind(pm.startTimeProperty().asString());
        readOnlyBox.selectedProperty().bindBidirectional(pm.readOnlyProperty());
        mandatoryBox.selectedProperty().bindBidirectional(pm.mandatoryProperty());
        labelField.textProperty().bindBidirectional(pm.labelProperty());

        //todo: setup bindings to businesscontrol
        businessControl.actualTimeProperty().bindBidirectional(pm.startTimeProperty()); //Bidirektional nur wenn Benutzer den Wert ändern kann
        businessControl.captionProperty().bind(pm.labelProperty());
    }

}
