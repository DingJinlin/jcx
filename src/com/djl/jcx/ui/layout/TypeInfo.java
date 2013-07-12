package com.djl.jcx.ui.layout;

import com.djl.jcx.data.model.TypeModel;
import com.djl.jcx.ui.ManageWindows;
import com.djl.jcx.ui.layout.form.AManageWindow;
import com.djl.jcx.ui.layout.form.TypeInfoManage;
import com.djl.jcx.ui.table.TypeTableLayout;
import com.djl.jcx.ui.table.container.AbstractContainerGetter;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;
import java.util.Set;

/**
 * User: Administrator
 * Date: 13-3-13
 * Time: 下午3:42
 */
public class TypeInfo extends VerticalLayout implements ITableInfo{
    public static final String S_TAB_CAPTION = "商品类型";
    private static final String S_CAPTION = "manage suit";
    private static final String S_SEARCH_CAPTION = "输入类型名缩写查询:";
    private static final String S_MSG_NO_SELECT = "未选择类型!";

    private static final String S_BT_ADD = "add";
    private static final String S_BT_MDF = "modify";
    private static final String S_BT_DEL = "delete";

    Layout queryRow;
    TypeTableLayout tableLayout;
    Layout buttons;

    public TypeInfo() {
        tableLayout = new TypeTableLayout();
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

    private HorizontalLayout initButtons(final TypeTableLayout tableLayout) {
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

        buttons.addComponent(butAdd);
        buttons.addComponent(butModify);
        buttons.addComponent(butDel);
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
        Set<TypeModel> select = tableLayout.getSelectValue();
        TypeModel model = select.iterator().next();

        Window manageWindow = initManageWindow(new TypeInfoManage(tableLayout, model));
        getWindow().addWindow(manageWindow);
    }

    /**
     * 显示增加窗口
     */
    public void showInsertManageWindow() {
        Window manageWindow = initManageWindow(new TypeInfoManage(tableLayout));
        getWindow().addWindow(manageWindow);
    }

    /**
     * 显示删除窗口
     */
    public void showDeleteWindow() {
        Set<TypeModel> select = tableLayout.getSelectValue();
        TypeModel model = select.iterator().next();
        try {
            tableLayout.getContainerGetter().delItem(model);
        } catch (Exception e) {
            getWindow().showNotification(e.toString(), Window.Notification.TYPE_WARNING_MESSAGE);
        }
    }

    @Override
    public void refreshTable() {
        tableLayout.getContainerGetter().refreshContainer();
    }
}
