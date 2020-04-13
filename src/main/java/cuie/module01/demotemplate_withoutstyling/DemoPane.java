package cuie.module01.demotemplate_withoutstyling;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * @author Dieter Holz
 */

//Spezieller Code
public class DemoPane extends StackPane {  //0. Auswahl des Basislayouts
	private Button button; //1. Deklaration aller Controls

	public DemoPane() {
		initializeParts();
		layoutParts();
	}

	private void initializeParts() {
		button = new Button("Hello World");
	} //2. Initialisierung aller Controls

	private void layoutParts() {
		getChildren().add(button);
	} //3. Anordnen der Controls
}
