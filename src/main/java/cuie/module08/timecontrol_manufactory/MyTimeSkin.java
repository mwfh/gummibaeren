package cuie.module08.timecontrol_manufactory;

import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Ellipse;
import javafx.util.converter.LocalTimeStringConverter;
import javafx.util.converter.TimeStringConverter;


class MyTimeSkin extends SkinBase<MyTimeControl> {
    // wird spaeter gebraucht
    private static final int ICON_SIZE  = 12;
    private static final int IMG_OFFSET = 4;

    private static ImageView invalidIcon = new ImageView(new Image(MyTimeSkin.class.getResource("icons/invalid.png").toExternalForm(),
                                                                   ICON_SIZE, ICON_SIZE,
                                                                   true, false));

    private static ImageView validIcon = new ImageView(new Image(MyTimeSkin.class.getResource("icons/valid.png").toExternalForm(),
                                                                   ICON_SIZE, ICON_SIZE,
                                                                   true, false));


    //todo: replace it
    //private Label placeHolder;
    private TextField editableTime;
    private Label captionLabel;
    private Ellipse ellipse_light;
    private Ellipse ellipse_dark;



    MyTimeSkin(MyTimeControl control) {
        super(control);
        initializeSelf();
        initializeParts();
        layoutParts();
        setupValueChangeListeners();
        setupBindings();
    }

    private void initializeSelf() {
        // getSkinnable().loadFonts("/fonts/Lato/Lato-Reg.ttf", "/fonts/Lato/Lato-Lig.ttf");
        getSkinnable().addStylesheetFiles("style.css");
    }

    private void initializeParts() {
        //placeHolder = new Label("To be replaced");
        editableTime = new TextField();
        editableTime.getStyleClass().add("editable-time");

        captionLabel = new Label();
        captionLabel.getStyleClass().add("caption-label");
    }

    private void layoutParts() {
        //getChildren().addAll(placeHolder);
        getChildren().addAll(new VBox(captionLabel, editableTime));
    }

    private void setupValueChangeListeners() {
    }

    private void setupBindings() {
        editableTime.textProperty().bindBidirectional(getSkinnable().actualTimeProperty(), new LocalTimeStringConverter()); //skin mit control verbinden
        captionLabel.textProperty().bind(getSkinnable().captionProperty());
    }
}
