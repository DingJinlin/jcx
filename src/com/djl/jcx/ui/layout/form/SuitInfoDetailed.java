package com.djl.jcx.ui.layout.form;

import com.djl.jcx.data.dao.IFactoryDao;
import com.djl.jcx.data.dao.ITypeDao;
import com.djl.jcx.data.model.FactoryModel;
import com.djl.jcx.data.model.SuitModel;
import com.djl.jcx.data.model.TypeModel;
import com.djl.jcx.ui.table.SuitTableLayout;
import com.djl.jcx.ui.table.container.AbstractContainerGetter;
import com.djl.jcx.ui.table.container.FactoryContainerGetter;
import com.djl.jcx.ui.table.container.SuitContainerGetter;
import com.djl.jcx.ui.table.container.TypeContainerGetter;
import com.djl.util.GB2Alpha;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;

import java.util.Arrays;
import java.util.Date;

/**
 * User: Administrator
 * Date: 13-3-13
 * Time: 下午3:42
 */
public class SuitInfoDetailed extends AManageWindow {
    Label lShowName;            // 显示名
    Label lInternationalCode;   // 条形码

    VerticalLayout layout;
    public SuitInfoDetailed(SuitModel model) {
        super();
        this.setSpacing(true);
        // 设置层属性
        layout = new VerticalLayout();
        layout.setMargin(true); // we want a margin
        layout.setSpacing(true); // and spacing between components

        this.addComponent(layout);

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

        layout.addComponent(lShowName);
        layout.addComponent(lInternationalCode);
    }

    String caption;
    @Override
    public String getTitle() {
        return caption;
    }
}