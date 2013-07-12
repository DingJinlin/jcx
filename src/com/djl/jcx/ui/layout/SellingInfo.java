package com.djl.jcx.ui.layout;

import com.djl.jcx.data.model.*;
import com.djl.jcx.ui.ManageWindows;
import com.djl.jcx.ui.layout.form.CheckoutInfoManage;
import com.djl.jcx.ui.table.*;
import com.djl.jcx.ui.table.container.DepositoryAddContainerGetter;
import com.djl.jcx.ui.table.container.DepositoryContainerGetter;
import com.djl.jcx.ui.table.container.SellingContainerGetter;
import com.djl.jcx.ui.table.container.SuitContainerGetter;
import com.vaadin.data.Property;
import com.vaadin.ui.*;

import java.util.Collection;
import java.util.Date;


/**
 * User: Administrator
 * Date: 13-3-13
 * Time: 下午3:42
 */

public class SellingInfo extends VerticalLayout implements ISelectModelInfo, ITableInfo {
    public static final String S_TAB_CAPTION = "商品销售";
    private static final String S_CAPTION = "manage selling";

    private static final String S_CAPTION_SUIT_CODE = "商品编码";
    private static final String S_BT_EXPLORE = "浏览";
    private static final String S_BT_RETURNS = "退货";

    private static final String S_BT_EDIT = "编辑";
    private static final String S_BT_SAVE = "保存";
    private static final String S_BT_CHECKOUT = "结帐";
    private static final String S_BT_REMOVE = "移除";

    private static final String S_MSG_NO_SUIT = "未找到此商品";
    private static final String S_MSG_NO_SELECT_SUIT = "未选择商品";
    private static final String S_MSG_NOT_ENOUGH = "库存数量不足";
    private static final String S_MSG_SUCCESS = "结账成功";

    TextField tfSuitCode;
    Button buExplore;
    SellingTableLayout tableLayout;
    Button butEdit;
    Button butRemove;
    Button butCheckout;

    public SellingInfo() {
        tableLayout = new SellingTableLayout();
        init();
    }

    private void init() {
        // 设置层属性
        setMargin(false);
        setSizeFull();

        this.addComponent(initInputZone());
        this.addComponent(initListZone());
        this.addComponent(initButtons());
    }

    /**
     * 初始化输入区
     * @return layout
     */
    private Layout initInputZone() {
        // 商品编码输入
        HorizontalLayout lSuitCode = new HorizontalLayout();
        lSuitCode.setSpacing(true);

        tfSuitCode = new TextField(S_CAPTION_SUIT_CODE);
        tfSuitCode.setImmediate(true);
        tfSuitCode.addListener(new SuitCodeListener());
        buExplore = new Button(S_BT_EXPLORE);
        SuitTableLayout suitTableLayout = new SuitTableLayout();
        buExplore.addListener(new ExplorerButListener(suitTableLayout));

        lSuitCode.addComponent(tfSuitCode);
        lSuitCode.addComponent(buExplore);
        lSuitCode.setComponentAlignment(buExplore, Alignment.BOTTOM_CENTER);

        // 输入区添加组件
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.addComponent(lSuitCode);

        return layout;
    }

    /** 由输入区选择的商品 */
    @Override
    public void setSelectSuit(SuitModel suit) {
        AdminModel admin = new AdminModel(1);
        SellingModel sellingModel = new SellingModel(admin, 1, new Date(), suit, suit.getSellingPrice());
        tableLayout.getContainerGetter().addItem(sellingModel);
    }

    /**
     * 初始化列表区
     * @return layout
     */
    private Layout initListZone() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.addComponent(tableLayout);

        return layout;
    }

    /**
     * 初始化按钮区
     * @return layout
     */
    private Layout initButtons() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);

        butEdit = new Button(S_BT_EDIT);
        butEdit.addListener(new EditButListener(tableLayout));

        butRemove = new Button(S_BT_REMOVE);
        butRemove.addListener(new RemoveButListener(tableLayout));

        butCheckout = new Button(S_BT_CHECKOUT);
        butCheckout.addListener(new CheckoutButListener());

        layout.addComponent(butEdit);
        layout.addComponent(butRemove);
        layout.addComponent(butCheckout);

        return layout;
    }

    /**
     * 显示选择商品窗口
     */
    public void showSelectSuitWindow() {
        SuitSelectTableLayout layout = new SuitSelectTableLayout(this);
        Window manageWindow = new ManageWindows(layout.getTitle(), layout, "800px", "80%");
        getWindow().addWindow(manageWindow);
    }

    /**
     * 显示结帐窗口
     */
    public void showCheckoutWindow() {
        CheckoutInfoManage layout = new CheckoutInfoManage(tableLayout);
        Window manageWindow = new ManageWindows(layout.getTitle(), layout, "800px", "80%");
        getWindow().addWindow(manageWindow);
    }

    @Override
    public void refreshTable() {
        //
    }

    /**
     * 商品编码输入事件处理
     */
    private class SuitCodeListener implements Property.ValueChangeListener {
        @Override
        public void valueChange(Property.ValueChangeEvent event) {
            if(!event.getProperty().getValue().equals("")) {
                SuitContainerGetter suitContainerGetter = new SuitContainerGetter();
                SuitModel suit =suitContainerGetter.searchItemByCode((String)event.getProperty().getValue());
                if(suit == null) {
                    getWindow().showNotification(S_MSG_NO_SUIT, Window.Notification.TYPE_WARNING_MESSAGE);
                } else {
                    AdminModel admin = new AdminModel(1);
                    SellingModel sellingModel = new SellingModel(SellingModel.EAction.SOLD, new Date(), suit, suit.getSellingPrice());
                    tableLayout.getContainerGetter().addItem(sellingModel);
                }
                event.getProperty().setValue("");
            }
        }
    }

    /**
     * 浏览按钮事件处理
     */
    private class ExplorerButListener implements Button.ClickListener {
        AbstractTableLayout tableLayout;

        ExplorerButListener(AbstractTableLayout tableLayout) {
            this.tableLayout = tableLayout;
        }

        @Override
        public void buttonClick(Button.ClickEvent event) {
            showSelectSuitWindow();
        }
    }

    /**
     * 编辑按钮事件处理
     */
    private class EditButListener implements Button.ClickListener {
        AbstractTableLayout tableLayout;
        DepositoryContainerGetter depositoryContainerGetter;

        EditButListener(AbstractTableLayout tableLayout) {
            this.tableLayout = tableLayout;
            depositoryContainerGetter = new DepositoryContainerGetter();
        }

        @Override
        public void buttonClick(Button.ClickEvent event) {
            Table table = tableLayout.getTable();
            table.setEditable(!table.isEditable());

            if(table.isEditable()) {
                event.getButton().setCaption(S_BT_SAVE);
            } else {
                event.getButton().setCaption(S_BT_EDIT);
            }
        }
    }

    /**
     * 结帐按钮事件处理
     */
//    private class CheckoutButListener implements Button.ClickListener {
//        AbstractTableLayout<AccessModel, IAccessDao> tableLayout;
//
//        CheckoutButListener(AbstractTableLayout<AccessModel, IAccessDao> tableLayout) {
//            this.tableLayout = tableLayout;
//        }
//
//        @Override
//        public void buttonClick(Button.ClickEvent event) {
//            SellingContainerGetter containerGetter = (SellingContainerGetter)tableLayout.getContainerGetter();
//            containerGetter.submitCheckout();
//            tableLayout.getContainerGetter().clearAll();
//            getWindow().showNotification(S_MSG_SUCCESS);
//        }
//    }

    private class CheckoutButListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            showCheckoutWindow();
        }
    }

    /**
     * 移除按钮事件处理
     */
    private class RemoveButListener implements Button.ClickListener {
        AbstractTableLayout tableLayout;

        RemoveButListener(AbstractTableLayout tableLayout) {
            this.tableLayout = tableLayout;
        }

        @Override
        public void buttonClick(Button.ClickEvent event) {
            tableLayout.removeSelectValue();
        }
    }
}
