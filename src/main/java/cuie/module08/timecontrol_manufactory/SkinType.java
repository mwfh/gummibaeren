package cuie.module08.timecontrol_manufactory;

import java.util.function.Function;

import javafx.scene.control.SkinBase;


public enum SkinType {
//    EXPERIMENTAL(MyTimeSkin::new);
EXPERIMENTAL(MyTimeSkinLight::new); //Standardskin setzen

    private final Function<MyTimeControl, SkinBase<MyTimeControl>> factory;

    SkinType(Function<MyTimeControl, SkinBase<MyTimeControl>> factory) {
        this.factory = factory;
    }

    public Function<MyTimeControl, SkinBase<MyTimeControl>> getFactory() {
        return factory;
    }

}
