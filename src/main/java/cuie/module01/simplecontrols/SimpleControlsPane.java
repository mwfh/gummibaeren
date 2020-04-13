package cuie.module01.simplecontrols;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SimpleControlsPane extends VBox {
	private Label     label;
	private Button    button;
	private TextField textField;
	private TextArea  textArea;

	public SimpleControlsPane() {
		initializeSelf();
		initializeParts();
		layoutParts();
	}

	private void initializeSelf(){
		Font.loadFont(getClass().getResourceAsStream("/fonts/Roboto/Roboto-Medium.ttf"),0);

		String stylesheet = getClass().getResource("style.css").toExternalForm();
		getStylesheets().add(stylesheet);
	}

	private void initializeParts() {
		label     = new Label("Ihr Name bitte");
		textField = new TextField("text field");
		textArea  = new TextArea("text area");
		button    = new Button("ein Button");
	}

	private void layoutParts() {
		setPadding(new Insets(5));
		setSpacing(5);

		setVgrow(textArea, Priority.ALWAYS);

		getChildren().addAll(label, textField, textArea, button);
	}

}
