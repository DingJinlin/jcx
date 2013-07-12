package com.djl.jcx.ui.layout;

import com.djl.jcx.data.model.SuitModel;
import com.djl.jcx.ui.ManageWindows;
import com.djl.jcx.ui.layout.form.AManageWindow;
import com.djl.jcx.ui.layout.form.SuitInfoManage;
import com.djl.jcx.ui.table.AbstractTableLayout;
import com.djl.jcx.ui.table.DepositoryTableLayout;
import com.djl.jcx.ui.table.SuitTableLayout;
import com.djl.jcx.ui.table.container.AbstractContainerGetter;
import com.djl.jcx.ui.table.container.DepositoryContainerGetter;
import com.vaadin.data.Property;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;

import java.util.Set;

/**
 * User: Administrator
 * Date: 13-3-13
 * Time: 下午3:42
 */
public class DepositoryInfo extends VerticalLayout implements ITableInfo {
    public static final String S_TAB_CAPTION = "商品存量";
    private static final String S_CAPTION = "manage depository";
    private static final String S_SEARCH_CODE_CAPTION = "商品编码";
    private static final String S_SEARCH_SHORT_CAPTION = "商品名称";

    Layout queryRow;
    DepositoryTableLayout tableLayout;

    public DepositoryInfo() {
        tableLayout = new DepositoryTableLayout();
        init();
    }

    private void init() {
        // 设置层属性
        setMargin(false);
        setSizeFull();


        queryRow = initQueryRow(tableLayout.getContainerGetter());

        addComponent(queryRow);
        addComponent(tableLayout);
    }

    private Layout initQueryRow(final AbstractContainerGetter containerGetter) {
        HorizontalLayout lytQuery = new HorizontalLayout();
        lytQuery.setMargin(true);
        lytQuery.setSpacing(true);

        TextField tfCode = new TextField(S_SEARCH_CODE_CAPTION);
        tfCode.setImmediate(true);
        tfCode.addListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                String code = (String)event.getProperty().getValue();
                if(!code.isEmpty()) {
                    ((DepositoryContainerGetter)containerGetter).fillContainerByCode(code);
                }
            }
        });

        TextField tfName = new TextField(S_SEARCH_SHORT_CAPTION);
        tfName.setImmediate(true);
        tfName.addListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                String name = event.getText();
                containerGetter.fillContainerByName(name);
            }
        });

        lytQuery.addComponent(tfCode);
        lytQuery.addComponent(tfName);

        return lytQuery;
    }

    /**
     * 刷新表格数据
     */
    @Override
    public void refreshTable() {
        tableLayout.getContainerGetter().refreshContainer();
    }
}
