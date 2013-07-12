package com.djl.jcx.ui.layout;

import com.djl.jcx.data.model.SuitModel;
import com.djl.jcx.ui.ManageWindows;
import com.djl.jcx.ui.layout.form.AManageWindow;
import com.djl.jcx.ui.layout.form.SuitInfoDelete;
import com.djl.jcx.ui.layout.form.SuitInfoDetailed;
import com.djl.jcx.ui.layout.form.SuitInfoManage;
import com.djl.jcx.ui.table.SuitTableLayout;
import com.djl.jcx.ui.table.container.AbstractContainerGetter;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;

import java.util.Set;

/**
 * User: Administrator
 * Date: 13-3-13
 * Time: 下午3:42
 */
public class SuitInfo extends VerticalLayout implements ITableInfo{
    public static final String S_TAB_CAPTION = "商品";
    private static final String S_CAPTION = "manage suit";
    private static final String S_SEARCH_CAPTION = "输入商品名缩写查询:";
    private static final String S_MSG_NO_SELECT = "未选择商品!";

    private static final String S_BT_ADD = "增加";
    private static final String S_BT_MDF = "修改";
    private static final String S_BT_DEL = "删除";
    private static final String S_BT_DET = "详细信息";

    Layout queryRow;
    SuitTableLayout tableLayout;
    Layout buttons;

    public SuitInfo() {
        tableLayout = new SuitTableLayout();
        init();
    }

    private void init() {
        // 设置层属性
        setMargin(false);
        setSizeFull();

        queryRow = initQueryRow(tableLayout.getContainerGetter());
        buttons = initButtons(tableLayout);

        addComponent(queryRow);
        addComponent(tableLayout);
        addComponent(buttons);
    }

    private HorizontalLayout initButtons(final SuitTableLayout tableLayout) {
        HorizontalLayout buttons = new HorizontalLayout ();
        buttons.setSpacing(true);

        Button butAdd = new Button(S_BT_ADD, new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                tableLayout.removeSelectValue();
                showInsertManageWindow();
            }
        });

        Button butModify = new Button(S_BT_MDF, new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Set selectValue = tableLayout.getSelectValue();
                if(selectValue.isEmpty()) {
                    clickEvent.getButton().getWindow().showNotification(S_MSG_NO_SELECT, Window.Notification.TYPE_WARNING_MESSAGE);
                } else {
                    showUpdateManageWindow();
                }
            }
        });

        Button butDel = new Button(S_BT_DEL, new Button.ClickListener(){
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Set selectValue = tableLayout.getSelectValue();
                if(selectValue.isEmpty()) {
                    event.getButton().getWindow().showNotification(S_MSG_NO_SELECT, Window.Notification.TYPE_WARNING_MESSAGE);
                } else {
                    showDeleteWindow();
                }
            }
        });

        Button butSuitDetailed = new Button(S_BT_DET, new Button.ClickListener(){
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Set selectValue = tableLayout.getSelectValue();
                if(selectValue.isEmpty()) {
                    event.getButton().getWindow().showNotification(S_MSG_NO_SELECT, Window.Notification.TYPE_WARNING_MESSAGE);
                } else {
                    showDetailedWindow();
                }
            }
        });

        buttons.addComponent(butAdd);
        buttons.addComponent(butModify);
        buttons.addComponent(butDel);
        buttons.addComponent(butSuitDetailed);
        return buttons;
    }

    private Window initManageWindow(AManageWindow manageWindowLayout) {
        return new ManageWindows(manageWindowLayout.getTitle(), manageWindowLayout);
    }

    private Layout initQueryRow(final AbstractContainerGetter containerGetter) {
        HorizontalLayout lytFactory = new HorizontalLayout();
        lytFactory.setMargin(true);
        lytFactory.setSpacing(true);

        TextField tfShortName = new TextField(S_SEARCH_CAPTION);
        tfShortName.setImmediate(true);
        tfShortName.addListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                String name = event.getText();
                containerGetter.fillContainerByName(name);
            }
        });

        lytFactory.addComponent(tfShortName);

        return lytFactory;
    }

    /**
     * 显示修改窗口
     */
    public void showUpdateManageWindow() {
        Set<SuitModel> select = tableLayout.getSelectValue();
        SuitModel model = select.iterator().next();

        Window manageWindow = initManageWindow(new SuitInfoManage(tableLayout, model));
        getWindow().addWindow(manageWindow);
    }

    /**
     * 显示增加窗口
     */
    public void showInsertManageWindow() {
        Window manageWindow = initManageWindow(new SuitInfoManage(tableLayout));
        getWindow().addWindow(manageWindow);
    }

    /**
     * 显示删除窗口
     */
    public void showDeleteWindow() {
        Set<SuitModel> select = tableLayout.getSelectValue();
        SuitModel model = select.iterator().next();
        AbstractContainerGetter containerGetter = tableLayout.getContainerGetter();
        Window delWindows = initManageWindow(new SuitInfoDelete(containerGetter, model));
        getWindow().addWindow(delWindows);
    }

    /**
     * 显示详细信息窗口
     */
    private void showDetailedWindow() {
        Set<SuitModel> select = tableLayout.getSelectValue();
        SuitModel model = select.iterator().next();
        Window detailedWindow = initManageWindow(new SuitInfoDetailed(model));
        getWindow().addWindow(detailedWindow);
    }

    @Override
    public void refreshTable() {
        tableLayout.getContainerGetter().refreshContainer();
    }
}
