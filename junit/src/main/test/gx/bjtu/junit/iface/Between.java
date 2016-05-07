package gx.bjtu.junit.iface;

import gx.bjtu.junit.model.BetweenSupplier;
import org.junit.experimental.theories.ParametersSuppliedBy;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by jean on 16-3-27.
 */
@Retention(RetentionPolicy.RUNTIME)
@ParametersSuppliedBy(BetweenSupplier.class)
public @interface Between {
    // 声明所有可用参数，效果为 @Between([first = int,] last = int)
    int first() default 0;  // 声明默认值，成为非必须提供项
    int last();
}
