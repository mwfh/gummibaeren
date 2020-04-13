package cuie.module01;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author Dieter Holz
 */
public class HelloWorld extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button button = new Button("Hello World");

        StackPane rootPane = new StackPane();
        rootPane.getChildren().add(button);

        //rootPane an Scene übergeben
        Scene myScene = new Scene(rootPane);

        primaryStage.setTitle("JavaFX App");
        primaryStage.setScene(myScene); //WICHTIG! Der Inhalt wird gesetzt
        primaryStage.setWidth(400);
        primaryStage.setHeight(300);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); //ruft init, start (UI wird aufgebaut) und stop automatisch zum richtigen Zeitpunkt auf. Nie selbst aufrufen!
    }
}
