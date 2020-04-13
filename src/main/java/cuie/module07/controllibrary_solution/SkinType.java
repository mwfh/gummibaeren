package cuie.module07.controllibrary_solution;

import java.util.function.Function;

import javafx.scene.control.SkinBase;

/**
 * @author Dieter Holz
 */
public enum SkinType {
    SLIM      (SlimSkin::new),
    LINEAR    (LinearSkin::new),
    PIE       (PieSkin::new),
    FRIDAY_FUN(FridayFunSkin::new);

    private final Function<NumberRangeControl, SkinBase<NumberRangeControl>> factory;

    SkinType(Function<NumberRangeControl, SkinBase<NumberRangeControl>> factory) {
        this.factory = factory;
    }

    public Function<NumberRangeControl, SkinBase<NumberRangeControl>> getFactory() {
        return factory;
    }
}
