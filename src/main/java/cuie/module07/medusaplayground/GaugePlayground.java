package cuie.module07.medusaplayground;

import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;

/**
 * @author Dieter Holz
 */
public class GaugePlayground extends Application {
    private static final Random RND = new Random();

    private Gauge  gauge;
    private Button button;

    @Override
    public void init() {
        gauge = GaugeBuilder.create()
                            .prefSize(500, 400)
                            // todo: add your customization here
                            .titleColor(Color.GREEN)

                            .build();

        button = new Button("Set Value");
        button.setOnMousePressed(event -> gauge.setValue(RND.nextDouble() * gauge.getRange() + gauge.getMinValue()));
    }

    @Override
    public void start(Stage stage) {
        HBox pane = new HBox(gauge, button);

        pane.setPadding(new Insets(10));
        pane.setSpacing(20);

        Scene scene = new Scene(pane);

        stage.setTitle("Gauge Playground");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

/*

.backgroundPaint(LinearGradient.valueOf("from 0% 0% to 0% 100%, rgb(38,38,38)  0% , rgb(15,15,15) 100%"))
.foregroundBaseColor(Color.rgb(249, 249, 249))
.knobColor(Color.BLACK)

.maxValue(1.0)
.valueVisible(false)
.sectionsVisible(true)
.sections(new Section(0.0, 0.2, Color.rgb(255, 10, 1)))

.minorTickMarksVisible(false)
.mediumTickMarksVisible(false)
.majorTickMarkType(TickMarkType.BOX)

.title("FUEL")
.tickLabelDecimals(1)

.needleSize(Gauge.NeedleSize.THICK)
.needleShape(Gauge.NeedleShape.ROUND)
.needleColor(Color.rgb(255, 61, 10))
.shadowsEnabled(true)

.angleRange(90)
.customTickLabelsEnabled(true)
.customTickLabels("E", "", "", "", "", "1/2", "", "", "", "", "F")

 */
