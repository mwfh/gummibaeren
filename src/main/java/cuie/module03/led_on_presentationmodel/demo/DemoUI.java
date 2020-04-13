package cuie.module03.led_on_presentationmodel.demo;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import cuie.module03.led_on_presentationmodel.LED;

/**
 * @author Dieter Holz
 */
public class DemoUI extends BorderPane {
    private LED    led;

    private Label dangerousLabel; //Deklaration
    private Button onButton;
    private Button offButton;

    private DemoPM pm;

    public DemoUI(DemoPM pm) {
        this.pm = pm; //PM speichern

        initializeParts();
        layoutParts();
        setupEventHandler();
        setupBindings();
    }


    private void initializeParts() {
        led       = new LED();

        dangerousLabel = new Label(); //initialisieren
        onButton  = new Button("On");
        offButton = new Button("Off");
    }

    private void layoutParts() {
        setCenter(led);

        VBox buttonBox = new VBox(10, dangerousLabel, onButton, offButton); //einfügen
        buttonBox.setPadding(new Insets(10));

        setRight(buttonBox);
    }

    private void setupEventHandler() { //Benutzerinteraktion
        // bei Bedarf ergaenzen
        //Properties in PM verändern, wenn Buttons gedrückt werden
        onButton.setOnAction(event -> pm.setDangerous(true));  //Bei Buttons: setOnAction()
        offButton.setOnAction(event -> pm.setDangerous(false));  //Bei Buttons: setOnAction()
    }

    private void setupBindings() {
        // bei Bedarf ergaenzen
        // dangerousProperty mit label, led und den beiden Buttons verbinden
        dangerousLabel.textProperty().bind(pm.dangerousProperty().asString());
        led.onProperty().bindBidirectional(pm.dangerousProperty());
        onButton.disableProperty().bind(pm.dangerousProperty()); //Sind beides Boolean-Properties
        offButton.disableProperty().bind(pm.dangerousProperty().not());
    }

}
