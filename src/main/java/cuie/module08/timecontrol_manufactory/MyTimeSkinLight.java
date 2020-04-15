
package cuie.module08.timecontrol_manufactory;

        import javafx.geometry.Pos;
        import javafx.scene.control.Label;
        import javafx.scene.control.SkinBase;
        import javafx.scene.control.TextField;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import javafx.scene.layout.HBox;
        import javafx.scene.layout.VBox;
        import javafx.scene.shape.Circle;
        import javafx.scene.shape.Ellipse;
        import javafx.scene.text.TextAlignment;
        import javafx.util.converter.LocalTimeStringConverter;


class MyTimeSkinLight extends SkinBase<MyTimeControl> {
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
    private Ellipse ellipseLight;
    private VBox oClockFrame;

    MyTimeSkinLight(MyTimeControl control) {
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
        double center = 100 * 0.5;

        //placeHolder = new Label("To be replaced");
        editableTime = new TextField();
        editableTime.getStyleClass().add("editable-time");

        captionLabel = new Label();
        captionLabel.getStyleClass().add("caption-label");


        ellipseLight = new Ellipse(center, center, 50,50);
        ellipseLight.getStyleClass().add("ellipse-light");

        oClockFrame = new VBox(editableTime, captionLabel);
        oClockFrame.setAlignment(Pos.CENTER);
    }

    private void layoutParts() {


        //getChildren().addAll(placeHolder);
        // getChildren().addAll(ellipseLight,editableTime, captionLabel);
        getChildren().addAll(ellipseLight,oClockFrame);

    }

    private void setupValueChangeListeners() {
    }

    private void setupBindings() {
        editableTime.textProperty().bindBidirectional(getSkinnable().actualTimeProperty(), new LocalTimeStringConverter()); //skin mit control verbinden
        captionLabel.textProperty().bind(getSkinnable().captionProperty());
    }

}
