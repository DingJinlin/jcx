package com.djl.jcx.ui.layout.form;

import com.djl.jcx.data.model.SuitModel;
import com.djl.jcx.ui.table.container.AbstractContainerGetter;
import com.vaadin.ui.*;

import java.util.Set;

/**
 * User: Administrator
 * Date: 13-3-13
 * Time: 下午3:42
 */
public class SuitInfoDelete extends AManageWindow {
    private static final String S_BT_CONFIRM = "确认";
    private static final String S_BT_CANCEL = "取消";

    Label lMsg = new Label("确认删除:");
    Label lShowName;            // 显示名
    Label lInternationalCode;   // 条形码

    Button btConfirm;
    Button btChannel;

    VerticalLayout infoLayout;
    HorizontalLayout butsLayout;
    AbstractContainerGetter containerGetter;

    public SuitInfoDelete(AbstractContainerGetter containerGetter, SuitModel model) {
        super();
        this.containerGetter = containerGetter;
        this.setSpacing(true);
        // 设置层属性
        infoLayout = new VerticalLayout();
        infoLayout.setMargin(true); // we want a margin
        infoLayout.setSpacing(true); // and spacing between components

        butsLayout = new HorizontalLayout();
        butsLayout.setMargin(true); // we want a margin
        butsLayout.setSpacing(true); // and spacing between components

        this.addComponent(infoLayout);
        this.addComponent(butsLayout);

        init(model);
    }

    private void init(final SuitModel model) {
        // 设置内容
        StringBuilder showName = new StringBuilder()
                .append(model.getName()).append(" ")
                .append(model.getStandard()).append(model.getUnit()).append(" ")
                .append(model.getSellingPrice()).append("元");
        lShowName = new Label(showName.toString());
        lInternationalCode = new Label(model.getInternationalCode());

        infoLayout.addComponent(lMsg);
        infoLayout.addComponent(lShowName);
        infoLayout.addComponent(lInternationalCode);

        btConfirm = new Button(S_BT_CONFIRM, new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                String errMsg = null;
                try {
                    containerGetter.delItem(model);
                    getWindow().getParent().removeWindow(event.getButton().getWindow());
                } catch (Exception e) {
                    errMsg = e.getMessage();
                    getWindow().showNotification(errMsg);
                }
            }
        });

        btChannel = new Button(S_BT_CANCEL, new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                getWindow().getParent().removeWindow(event.getButton().getWindow());
            }
        });

        butsLayout.addComponent(btChannel);
        butsLayout.addComponent(btConfirm);
    }

    String caption;

    @Override
    public String getTitle() {
        return caption;
    }
}