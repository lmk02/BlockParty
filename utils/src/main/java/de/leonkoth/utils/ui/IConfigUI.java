package de.leonkoth.utils.ui;

import org.bukkit.Material;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Package de.leonkoth.blockparty.util
 *
 * @author Leon Koth
 * © 2019
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IConfigUI {

    String title();

    String[] description();

    Material infoItem();

    Material onItem();

    Material offItem();

    boolean useVarNameAsTitle() default false;

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface UIInfo {

        String prefix() default "";

        String suffix() default "";

        Material leftItem();

        Material rightItem();

        String leftItemTitle();

        String rightItemTitle();

    }

}
