package com.djl.jcx.ui.layout;

import com.djl.jcx.data.dao.IDepositoryDao;
import com.djl.jcx.data.model.DepositoryModel;
import com.djl.jcx.data.model.SuitModel;
import com.djl.jcx.ui.ManageWindows;
import com.djl.jcx.ui.table.AbstractTableLayout;
import com.djl.jcx.ui.table.DepositoryAddTableLayout;
import com.djl.jcx.ui.table.*;
import com.djl.jcx.ui.table.container.*;
import com.vaadin.data.Property;
import com.vaadin.ui.*;


/**
 * User: Administrator
 * Date: 13-3-13
 * Time: 下午3:42
 */
public class DepositoryAddInfo extends VerticalLayout implements ISelectModelInfo, ITableInfo {
    public static final String S_TAB_CAPTION = "商品入库";
    private static final String S_CAPTION = "manage depository";

    private static final String S_CAPTION_SUIT_CODE = "商品编码";
    private static final String S_CAPTION_SUIT_NAME = "商品名称";
    private static final String S_CAPTION_ADD_AMOUNT = "入库数量";
    private static final String S_CAPTION_COMMENT = "备注";

    private static final String S_BT_EXPLORE = "浏览";
    private static final String S_BT_ADD = "增加";
    private static final String S_BT_CLEAR = "清除";

    private static final String S_BT_SUBMIT = "入库";
    private static final String S_BT_REMOVE = "移除";

    private static final String S_MSG_NO_SUIT = "未找到此商品";
    private static final String S_MSG_NO_SELECT_SUIT = "未选择商品";
    private static final String S_MSG_NO_INPUT_AMOUNT = "入库数量为空";
    private static final String S_MSG_SUCCESS = "入库成功";

    TextField tfSuitCode;
    TextField tfSuitName;
    TextField tfAddAmount;
    TextField tfComment;

    Button buExplore;
    Button btAdd;
    Button btClear;
    DepositoryAddTableLayout tableLayout;
    Button butSubmit;
    Button butRemove;

    SuitContainerGetter suitContainerGetter;

    public DepositoryAddInfo() {
        init();

        suitContainerGetter = new SuitContainerGetter();
    }

    private void init() {
        // 设置层属性
        setMargin(false);
        setSizeFull();

        tableLayout = new DepositoryAddTableLayout();
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


        // 商品名称输入
        tfSuitName = new TextField(S_CAPTION_SUIT_NAME);
        tfSuitName.setEnabled(false);

        // 入库数量输入
        tfAddAmount = new TextField(S_CAPTION_ADD_AMOUNT);

        // 备注输入
        tfComment = new TextField(S_CAPTION_COMMENT);

        // 揿钮区
        HorizontalLayout lButtons = new HorizontalLayout();
        lButtons.setSpacing(true);

        // 增加按钮
        btAdd = new Button(S_BT_ADD);
        btAdd.addListener(new AddButListener(tableLayout));

        // 清除按钮
        btClear = new Button(S_BT_CLEAR);
        btClear.addListener(new ClearButListener());

        lButtons.addComponent(btAdd);
        lButtons.addComponent(btClear);

        // 输入区添加组件
        GridLayout layout = new GridLayout(4, 2);
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);

        layout.setColumnExpandRatio(3, 1f);

        layout.addComponent(lSuitCode, 0, 0);
        layout.addComponent(tfSuitName, 1, 0);
        layout.addComponent(tfAddAmount, 2, 0);
        layout.addComponent(tfComment, 3, 0);
        layout.addComponent(lButtons, 0, 1);

        return layout;
    }

    private SuitModel selectSuit;
    /**
     * 初始化输入区数据
     * @param model 模型
     */
    public void initInputZoneData(DepositoryModel model) {
        String suitId = "";
        String suitCode = "";
        String suitName = "";
        String amount = "";
        String comment = "";

        selectSuit = model.getSuit();
        if(selectSuit != null) {
            suitId = String.valueOf(selectSuit.getId());
            suitCode = selectSuit.getInternationalCode();
            suitName = selectSuit.getName();
            amount = String.valueOf(model.getAmount());
        }

        tfSuitCode.setValue(suitCode);
        tfSuitName.setValue(suitName);
        tfAddAmount.setValue(amount);
        tfComment.setValue(comment);
    }

    @Override
    public void setSelectSuit(SuitModel model) {
        DepositoryModel depositoryModel = new DepositoryModel(0, model);
        initInputZoneData(depositoryModel);
    }

    /**
     * 清除输入区数据
     */
    public void clearInputZoneData() {
        selectSuit = null;
        String suitId = "";
        String suitCode = "";
        String suitName = "";
        String amount = "";
        String comment = "";

        tfSuitCode.setValue(suitCode);
        tfSuitName.setValue(suitName);
        tfAddAmount.setValue(amount);
        tfComment.setValue(comment);
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

        butSubmit = new Button(S_BT_SUBMIT);
        butSubmit.addListener(new SubmitButListener(tableLayout));
        butRemove = new Button(S_BT_REMOVE);
        butRemove.addListener(new RemoveButListener(tableLayout));

        layout.addComponent(butRemove);
        layout.addComponent(butSubmit);

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
            if(selectSuit == null && !event.getProperty().getValue().equals("")) {
                SuitModel suitModel =suitContainerGetter.searchItemByCode((String)event.getProperty().getValue());
                if(suitModel == null) {
                    getWindow().showNotification(S_MSG_NO_SUIT, Window.Notification.TYPE_WARNING_MESSAGE);
                } else {
                    DepositoryModel model = new DepositoryModel(0, suitModel);
                    initInputZoneData(model);
                }
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
     * 增加按钮事件处理
     */
    private class AddButListener implements Button.ClickListener {
        AbstractTableLayout<DepositoryModel, IDepositoryDao> tableLayout;

        AddButListener(AbstractTableLayout<DepositoryModel, IDepositoryDao> tableLayout) {
            this.tableLayout = tableLayout;
        }

        @Override
        public void buttonClick(Button.ClickEvent event) {

            if(tfAddAmount.getValue() == null ||
                tfAddAmount.getValue().equals("0") ||
                tfAddAmount.getValue().equals("")) {
                getWindow().showNotification(S_MSG_NO_INPUT_AMOUNT, Window.Notification.TYPE_WARNING_MESSAGE);
            } else if(selectSuit == null) {
                getWindow().showNotification(S_MSG_NO_SELECT_SUIT, Window.Notification.TYPE_WARNING_MESSAGE);
            } else {
                int amount = Integer.valueOf((String)tfAddAmount.getValue());
//                String comment = (String)tfComment.getValue();

                DepositoryModel model = new DepositoryModel(amount, selectSuit);
                tableLayout.getContainerGetter().addItem(model);
                clearInputZoneData();
            }
        }
    }

    /**
     * 增加按钮事件处理
     */
    private class ClearButListener implements Button.ClickListener {
        @Override
        public void buttonClick(Button.ClickEvent event) {
            clearInputZoneData();
        }
    }

    /**
     * 提交按钮事件处理
     */
    private class SubmitButListener implements Button.ClickListener {
        AbstractTableLayout<DepositoryModel, IDepositoryDao> tableLayout;

        SubmitButListener(AbstractTableLayout<DepositoryModel, IDepositoryDao> tableLayout) {
            this.tableLayout = tableLayout;
        }

        @Override
        public void buttonClick(Button.ClickEvent event) {
            DepositoryAddContainerGetter containerGetter = (DepositoryAddContainerGetter)tableLayout.getContainerGetter();
            containerGetter.submitDepository();
            tableLayout.getContainerGetter().clearAll();
            getWindow().showNotification(S_MSG_SUCCESS);
        }
    }

    /**
     * 移除按钮事件处理
     */
    private class RemoveButListener implements Button.ClickListener {
        AbstractTableLayout<DepositoryModel, IDepositoryDao> tableLayout;

        RemoveButListener(AbstractTableLayout<DepositoryModel, IDepositoryDao> tableLayout) {
            this.tableLayout = tableLayout;
        }

        @Override
        public void buttonClick(Button.ClickEvent event) {
            tableLayout.removeSelectValue();
        }
    }
}
