package com.djl.jcx.ui;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Window;

/**
 * User: Ding
 * Date: 13-3-30
 * Time: 下午4:12
 */
public class ManageWindows extends Window {
    String DEFAULT_WIDTH = "500px";
    String DEFAULT_HEIGHT = "80%";

    private void initWindows() {
        this.center();
        this.setModal(true);
        this.setResizable(false);
        this.setDraggable(false);
        this.setResizeLazy(true);
    }

    public ManageWindows(String caption, ComponentContainer content) {
        super(caption, content);
        initWindows();

        this.setWidth(DEFAULT_WIDTH);
        this.setHeight(DEFAULT_WIDTH);
    }

    public ManageWindows(String caption, ComponentContainer content, String width, String height) {
        super(caption, content);
        initWindows();

        this.setWidth(width);
        this.setHeight(height);
    }
}
