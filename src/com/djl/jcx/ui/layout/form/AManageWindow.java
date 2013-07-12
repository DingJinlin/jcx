package com.djl.jcx.ui.layout.form;

import com.vaadin.ui.VerticalLayout;

/**
 * User: Ding
 * Date: 13-3-30
 * Time: 下午4:24
 */
public abstract class AManageWindow extends VerticalLayout {
    public static final String BUT_DISCARD = "discard";
    public static final String BUT_APPLY = "apply";

    public abstract String getTitle();

    protected AManageWindow() {
        this.setSizeFull();
        this.setMargin(true);
    }
}
