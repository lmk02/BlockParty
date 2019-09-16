package de.leonkoth.utils.ui;

import lombok.Getter;
import lombok.Setter;

/**
 * Package de.leonkoth.utils.ui
 *
 * @author Leon Koth
 * © 2019
 */
public class UIObject {

    @Getter
    private Object uiObject;

    @Getter
    @Setter
    private String uiTitle;

    public UIObject(Object uiObject, String uiTitle)
    {
        this.uiObject = uiObject;
        this.uiTitle = uiTitle;
    }

}
