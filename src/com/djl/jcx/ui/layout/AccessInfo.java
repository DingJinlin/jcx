package com.djl.jcx.ui.layout;

import com.djl.jcx.data.dao.IDepositoryDao;
import com.djl.jcx.data.model.AccessModel;
import com.djl.jcx.data.model.DepositoryModel;
import com.djl.jcx.data.model.SuitModel;
import com.djl.jcx.ui.ManageWindows;
import com.djl.jcx.ui.table.*;
import com.djl.jcx.ui.table.container.AbstractContainerGetter;
import com.djl.jcx.ui.table.container.AccessContainerGetter;
import com.djl.jcx.ui.table.container.DepositoryContainerGetter;
import com.djl.jcx.ui.table.container.SuitContainerGetter;
import com.vaadin.data.Property;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * User: Administrator
 * Date: 13-3-13
 * Time: 下午3:42
 */
public class AccessInfo extends VerticalLayout implements ISelectModelInfo, ITableInfo {
//    public static final String S_TAB_CAPTION = "商品进出记录";
    public static final String S_TAB_CAPTION = "商品销售记录";
    private static final String S_CAPTION = "商品销售记录查询";
    private static final String S_CAPTION_SUIT_CODE = "商品编码";
    private static final String S_CAPTION_SUIT_NAME = "商品名称";
    private static final String S_BT_EXPLORE = "浏览";

    private static final String S_SEARCH_START_TIME_CAPTION = "销售起始日期";
    private static final String S_SEARCH_END_TIME_CAPTION = "销售截止日期";
    private static final String S_SEARCH_FIND_BT_CAPTION = "查询";
    private static final String S_BT_CLEAR = "清除";

    private static final String S_MSG_NO_SUIT = "未找到此商品";
    private static final String S_MSG_NO_SELECT_SUIT = "未选择商品";

    AccessTableLayout tableLayout;

    TextField tfSuitCode;
    TextField tfSuitName;
    PopupDateField pdfStartDatetime = null;
    PopupDateField pdfEndDatetime = null;

    SuitContainerGetter suitContainerGetter;
    AccessContainerGetter containerGetter;

    public AccessInfo() {
        tableLayout = new AccessTableLayout();
        init();
    }

    private void init() {
        suitContainerGetter = new SuitContainerGetter();
        containerGetter = (AccessContainerGetter)tableLayout.getContainerGetter();
        // 设置层属性
        setMargin(false);
        setSizeFull();

        this.addComponent(initInputZone());
        this.addComponent(initListZone());

        addComponent(tableLayout);
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
        Button buExplore = new Button(S_BT_EXPLORE);
        SuitTableLayout suitTableLayout = new SuitTableLayout();
        buExplore.addListener(new ExplorerButListener(suitTableLayout));

        lSuitCode.addComponent(tfSuitCode);
        lSuitCode.addComponent(buExplore);
        lSuitCode.setComponentAlignment(buExplore, Alignment.BOTTOM_CENTER);

        // 商品名称输入
        tfSuitName = new TextField(S_CAPTION_SUIT_NAME);
        tfSuitName.setEnabled(false);

        // 设置默认日期
//        Calendar startCalendar = new GregorianCalendar();
//        startCalendar.setTimeInMillis(System.currentTimeMillis());
//        startCalendar.set(Calendar.HOUR, 0);
//        startCalendar.set(Calendar.MINUTE, 0);
//        startCalendar.set(Calendar.SECOND, 0);
//        startCalendar.set(Calendar.MILLISECOND, 0);
//
//        Calendar endCalendar = new GregorianCalendar();
//
//        pdfStartDatetime = new PopupDateField(S_SEARCH_START_TIME_CAPTION, startCalendar.getTime());
//        pdfEndDatetime = new PopupDateField(S_SEARCH_END_TIME_CAPTION, endCalendar.getTime());

        pdfStartDatetime = new PopupDateField(S_SEARCH_START_TIME_CAPTION);
        pdfEndDatetime = new PopupDateField(S_SEARCH_END_TIME_CAPTION);

        // 揿钮区
        HorizontalLayout lButtons = new HorizontalLayout();
        lButtons.setSpacing(true);

        // 增加按钮
        Button btFind = new Button(S_SEARCH_FIND_BT_CAPTION);
        btFind.addListener(new FindButListener());

        // 清除按钮
        Button btClear = new Button(S_BT_CLEAR);
        btClear.addListener(new ClearButListener());

        lButtons.addComponent(btFind);
        lButtons.addComponent(btClear);

        // 输入区添加组件
        GridLayout layout = new GridLayout(4, 2);
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);

        layout.setColumnExpandRatio(3, 1f);

        layout.addComponent(lSuitCode, 0, 0);
        layout.addComponent(tfSuitName, 1, 0);
        layout.addComponent(pdfStartDatetime, 2, 0);
        layout.addComponent(pdfEndDatetime, 3, 0);
        layout.addComponent(lButtons, 0, 1);

        return layout;
    }

    private SuitModel selectSuit;
    /**
     * 初始化输入区数据
     * @param model 模型
     */
    public void initInputZoneData(AccessModel model) {
        String suitId = "";
        String suitCode = "";
        String suitName = "";

        selectSuit = model.getSuit();
        if(selectSuit != null) {
            suitId = String.valueOf(selectSuit.getId());
            suitCode = selectSuit.getInternationalCode();
            suitName = selectSuit.getName();
        }

        tfSuitCode.setValue(suitCode);
        tfSuitName.setValue(suitName);
        pdfStartDatetime.setValue(model.getDateCondition1());
        pdfEndDatetime.setValue(model.getDateCondition2());
    }

    @Override
    public void setSelectSuit(SuitModel model) {
        AccessModel accessModel = new AccessModel(AccessModel.EAction.SOLD, null, null, model);
        initInputZoneData(accessModel);
    }

    /**
     * 清除输入区数据
     */
    public void clearInputZoneData() {
        selectSuit = null;
        String suitId = "";
        String suitCode = "";
        String suitName = "";
        String comment = "";

        tfSuitCode.setValue(suitCode);
        tfSuitName.setValue(suitName);
        pdfStartDatetime.setValue(null);
        pdfEndDatetime.setValue(null);
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
     * 刷新表格数据
     */
    @Override
    public void refreshTable() {
    }

    /**
     * 商品编码输入事件处理
     */
    private class SuitCodeListener implements Property.ValueChangeListener {
        @Override
        public void valueChange(Property.ValueChangeEvent event) {
            if(!event.getProperty().getValue().equals("")) {
                SuitModel suitModel =suitContainerGetter.searchItemByCode((String)event.getProperty().getValue());
                if(suitModel == null) {
                    getWindow().showNotification(S_MSG_NO_SUIT, Window.Notification.TYPE_WARNING_MESSAGE);
                } else {
                    AccessModel accessModel = new AccessModel(AccessModel.EAction.SOLD, null, null, suitModel);
                    initInputZoneData(accessModel);
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
     * 查询按钮事件处理
     */
    private class FindButListener implements Button.ClickListener {
        AbstractTableLayout<DepositoryModel, IDepositoryDao> tableLayout;

        @Override
        public void buttonClick(Button.ClickEvent event) {
            Integer suitId = null;
            if(selectSuit != null) {
                suitId = selectSuit.getId();
            }
            Date startData = (Date)pdfStartDatetime.getValue();
            Date endData = (Date)pdfEndDatetime.getValue();

            containerGetter.clearAll();
            containerGetter.fillContainerByParam(suitId, startData, endData);
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
     * 显示选择商品窗口
     */
    public void showSelectSuitWindow() {
        SuitSelectTableLayout layout = new SuitSelectTableLayout(this);
        Window manageWindow = new ManageWindows(layout.getTitle(), layout, "800px", "80%");
        getWindow().addWindow(manageWindow);
    }
}
