package cuie.module03.led_and_slider.demo;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import cuie.module03.led_and_slider.LED;
import cuie.module03.led_and_slider.demo.DemoPM;

/**
 * @author Dieter Holz
 */
public class DemoUI extends BorderPane {
    private final DemoPM pm;

    private       LED    led;
    private       Button onButton;
    private       Button offButton;

    private Slider slider;  //3. UI-Element deklarieren
    private Label label;

    public DemoUI(DemoPM pm) {
        this.pm = pm;
        initializeSelf();
        initializeParts();
        layoutParts();
        setupEventHandler();
        setUpValuechangeListener();
        setupBindings();
    }

    private void initializeParts() {
        led       = new LED();
        onButton  = new Button("On");
        offButton = new Button("Off");
        slider = new Slider();  //4. UI-Element initialisieren
        slider.setShowTickLabels(true); //Skala
        label = new Label();
    }

    private void initializeSelf() {
        String stylesheet = getClass().getResource("style.css").toExternalForm();
        getStylesheets().add(stylesheet);
    }

    private void layoutParts() {
        setCenter(led);

        VBox buttonBox = new VBox(10, slider, label, onButton, offButton); //5. UI-Element hinzufügen
        buttonBox.setPadding(new Insets(10));

        setRight(buttonBox);
    }

    private void setupEventHandler() {
        onButton.setOnAction(event -> pm.setInDangerZone(true));
        offButton.setOnAction(event -> pm.setInDangerZone(false));
    }

    private void setUpValuechangeListener(){
        pm.sliderValueProperty().addListener((observable, oldValue, newValue) -> {
            if(pm.getSliderValue() >= 75){
                pm.setInDangerZone(true);
            }else{
                pm.setInDangerZone(false);
            }
        });
    }

    private void setupBindings(){
        led.onProperty().bindBidirectional(pm.inDangerZoneProperty());

        onButton.disableProperty().bind(pm.inDangerZoneProperty());
        offButton.disableProperty().bind(pm.inDangerZoneProperty().not());

        slider.valueProperty().bindBidirectional(pm.sliderValueProperty()); //6. Elemente verbinden
        label.textProperty().bind(pm.sliderValueProperty().asString());
    }

}
