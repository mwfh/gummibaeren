package cuie.module05.keyframe_playground;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * @author Dieter Holz
 */
public class KeyframePlayground extends BorderPane {

	private Circle    circle;
	private Text      text;
	private Rectangle rectangle;
	private Line      line;
	private Path      path;
	private Rectangle pathFinder;
	private Ellipse   ellipse;

	private Timeline timeline = new Timeline(); //mind. 1 Timeline nötig um mit keyframes Animationen machen zu können

	public KeyframePlayground() {
		initializeControls();
		layoutControls();
		addEventHandlers();
		addValueChangedListeners();
		addBindings();
	}

	private void initializeControls() {
		rectangle = new Rectangle(100, 100, Color.CORNFLOWERBLUE);
		rectangle.setStroke(Color.RED);
		rectangle.setStrokeWidth(0);
		rectangle.relocate(50, 50);

		circle = new Circle(250, 200, 30,
		                    new RadialGradient(0, 0, 0.2, 0.3, 1, true, CycleMethod.NO_CYCLE,
		                                       new Stop(0, Color.rgb(250, 250, 255)),
		                                       new Stop(1, Color.CORNFLOWERBLUE)));
		//add a shadow effect
		circle.setEffect(new InnerShadow(7, Color.CORNFLOWERBLUE.darker().darker()));
		//change a cursor when it is over circle
		circle.setCursor(Cursor.HAND);

		text = new Text(50, 300, "Hi!");
		text.setFont(new Font(48));

		line = new Line(50, 400, 300, 400);
		line.setStrokeWidth(3);

		path = new Path(new MoveTo(220, 20),
		                new CubicCurveTo(580, 0, 420, 120, 320, 80),
		                new CubicCurveTo(200, 40, 200, 240, 420, 120));
		path.setStroke(Color.DODGERBLUE);
		path.getStrokeDashArray().setAll(5d, 5d);

		pathFinder = new Rectangle(30, 10, Color.CORNFLOWERBLUE);
		pathFinder.setTranslateX(205);
		pathFinder.setTranslateY(15);

		ellipse = new Ellipse(400, 400, 50, 30);
		ellipse.setStrokeWidth(3);

	}

	private void layoutControls() {
		setPadding(new Insets(10));

		Pane drawingPane = new Pane();
		drawingPane.setPrefHeight(500);
		drawingPane.setPrefWidth(500);
		drawingPane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		drawingPane.setBorder(new Border(new BorderStroke(Color.web("#0E53A7"), BorderStrokeStyle.SOLID, null, null)));

		drawingPane.getChildren().addAll(circle, text, rectangle, line, path, pathFinder, ellipse);

		setCenter(drawingPane);
	}

	//Hier werden Keyframes verwendet
	private void addEventHandlers() {
		//Rectangle:
		rectangle.setOnMouseClicked(event -> {
            if(timeline.getStatus().equals(Animation.Status.RUNNING)){ //Wenn Animation schon läuft
                return; //aufhören
            }
            //Sonst: (nur wenn Animation noch nicht läuft)
            DoubleProperty animatedProperty = rectangle.strokeWidthProperty();

            //1. initialisieren
            KeyValue endValue = new KeyValue(animatedProperty, 12.0);
            KeyValue secondValue = new KeyValue(rectangle.widthProperty(), 200);
            KeyValue thirdValue = new KeyValue(rectangle.heightProperty(), 300); //(was sich ändern soll, Zielvalue)

			//2. hinzufügen (Werden parallel ausgeführt)
            KeyFrame frame = new KeyFrame(Duration.seconds(0.5), endValue, secondValue, thirdValue); // (Dauer, keyvalues)
			//Aktuelle Keyframesliste
            timeline.getKeyFrames().setAll(frame);

            //3. Wie oft
            timeline.setCycleCount(6); //Wie oft Animation ausgeführt wird
            timeline.setAutoReverse(true); //Hin und Zurück aktivieren

			//4. Play
            timeline.play();

		});

		circle.setOnMouseClicked(event -> {
			if (timeline.getStatus().equals(Animation.Status.RUNNING)) {
				return;
			}

			DoubleProperty animatedProperty = circle.radiusProperty();

			KeyValue endValue = new KeyValue(animatedProperty, circle.getRadius() * 2);

			KeyFrame endFrame = new KeyFrame(Duration.millis(500), endValue);

			timeline.getKeyFrames().setAll(endFrame);
			timeline.setAutoReverse(true);
			timeline.setCycleCount(6);

			timeline.play();
		});

		text.setOnMouseClicked(event -> {
		});

		line.setOnMouseClicked(event -> {
			if (timeline.getStatus().equals(Animation.Status.RUNNING)) {
				return;
			}

			timeline.getKeyFrames().setAll(new KeyFrame(Duration.millis(500),
			                                            new KeyValue(line.endXProperty(), line.getEndX() + 100),
			                                            new KeyValue(line.endYProperty(), line.getEndY() + 100)));
			timeline.setAutoReverse(true);
			timeline.setCycleCount(6);

			timeline.play();

		});

		pathFinder.setOnMouseClicked(event -> {
		});

		ellipse.setOnMouseClicked(event -> {
		});
	}

	private void addValueChangedListeners() {
	}

	private void addBindings() {
	}

}
