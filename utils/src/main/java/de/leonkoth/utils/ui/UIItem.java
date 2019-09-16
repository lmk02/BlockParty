package de.leonkoth.utils.ui;

import lombok.Getter;

/**
 * Package de.leonkoth.utils.ui
 *
 * @author Leon Koth
 * © 2019
 */
public class UIItem {

    @Getter
    private IConfigUI uiElement;

    @Getter
    private Type type;

    @Getter
    private Object value;

    @Getter
    private String varName;

    public UIItem(IConfigUI uiElement, Object value, String varName)
    {
        this.uiElement = uiElement;
        this.value = value;
        this.varName = varName;

        if(this.value.getClass() == boolean.class || this.value.getClass() == Boolean.class)
        {
            this.type = Type.BOOL;
        } else {
            this.type = Type.NUMBER;
        }
    }

    public enum Type {
        BOOL, NUMBER, LIST
    }

}
