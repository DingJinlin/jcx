package com.djl.jcx.ui.layout.form;

import com.djl.jcx.data.dao.IAccessDao;
import com.djl.jcx.data.dao.ISellingDao;
import com.djl.jcx.data.model.AccessModel;
import com.djl.jcx.data.model.SellingModel;
import com.djl.jcx.ui.table.AbstractTableLayout;
import com.djl.jcx.ui.table.container.SellingContainerGetter;
import com.vaadin.data.Property;
import com.vaadin.ui.*;

import java.util.Collection;

/**
 * User: Administrator
 * Date: 13-3-13
 * Time: 下午3:42
 */
public class CheckoutInfoManage extends AManageWindow {
    // 参数常量定义
    private static final String COMMON_FIELD_WIDTH = "12em";

    // 字符串常量定义
    // 结账界面
    private static final String S_CAPTION_SHOULD_PAID  = "应付";
    private static final String S_CAPTION_REDUCE_PAID  = "减价";
    private static final String S_CAPTION_ACTUALLY_PAID = "实付";
    private static final String S_CAPTION_GAVE_CHANGE = "找零";

    TextField tfShouldPaid;
    TextField tfReducePaid;
    TextField tfActuallyPaid;
    TextField tfGaveChange;


    AbstractTableLayout<SellingModel, ISellingDao> tableLayout;

    Button btDiscardChanges;
    Button btApply;

    public CheckoutInfoManage(AbstractTableLayout<SellingModel, ISellingDao> tableLayout) {
        super();
        this.tableLayout = tableLayout;

        init();
    }

    /* 应付 */
    float pricePaid;

    /* 减价 */
    float reducePricePaid;

    private void init() {
        pricePaid = countPrice();
        reducePricePaid = countReducePrice();
        Layout layout = new VerticalLayout();

        // 应付
        tfShouldPaid = new TextField(S_CAPTION_SHOULD_PAID);
        tfShouldPaid.setEnabled(false);
        tfShouldPaid.setValue(pricePaid);
        tfShouldPaid.setWidth("100%");

        // 减价
        tfReducePaid = new TextField(S_CAPTION_REDUCE_PAID);
        tfReducePaid.setEnabled(false);
        tfReducePaid.setValue(reducePricePaid);
        tfReducePaid.setWidth("100%");

        // 实付
        tfActuallyPaid = new TextField(S_CAPTION_ACTUALLY_PAID);
        tfActuallyPaid.setImmediate(true);
        tfActuallyPaid.addListener(new ActuallyListener());
        tfActuallyPaid.setWidth("100%");

        // 找零
        tfGaveChange = new TextField(S_CAPTION_GAVE_CHANGE);
        tfGaveChange.setValue(0f);
        tfGaveChange.setEnabled(false);
        tfGaveChange.setWidth("100%");

        layout.addComponent(tfShouldPaid);
        layout.addComponent(tfReducePaid);
        layout.addComponent(tfActuallyPaid);
        layout.addComponent(tfGaveChange);

        addComponent(layout);

        // The cancel / btApply buttons
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        btDiscardChanges = new Button(BUT_DISCARD,
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Window subWin = getWindow();
                        subWin.getParent().removeWindow(subWin);
                    }
                });

        btApply = new Button(BUT_APPLY, new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    SellingContainerGetter containerGetter = (SellingContainerGetter)tableLayout.getContainerGetter();
                    containerGetter.submitCheckout();

                    containerGetter.getContainer().removeAllItems();
                    Window subWin = getWindow();
                    subWin.getParent().removeWindow(subWin);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btApply.setEnabled(false);

        buttons.addComponent(btDiscardChanges);
        buttons.setComponentAlignment(btDiscardChanges, Alignment.MIDDLE_LEFT);
        buttons.addComponent(btApply);

        layout.addComponent(buttons);
    }

    String caption;
    @Override
    public String getTitle() {
        return caption;
    }

    /**
     * 计算总价
     * @return 总价
     */
    private float countPrice() {
        Collection<SellingModel> sellingModels = tableLayout.getContainerGetter().getContainer().getItemIds();

        float sum = 0;
        for(SellingModel model : sellingModels) {
            float unit_sum = model.getPrice() * model.getCount();
            sum += unit_sum;
        }

        return sum;
    }

    /**
     * 获取总的减价
     * @return 总的减价金额
     */
    private float countReducePrice() {
        Collection<SellingModel> sellingModels = tableLayout.getContainerGetter().getContainer().getItemIds();

        float sum = 0;
        for(SellingModel model : sellingModels) {
            float unit_sum = model.getPrice() * model.getCount();
            sum += unit_sum;
        }

        sum = sum - countPrice();
        return sum;
    }

    /**
     * 实付文本框事件监听
     */
    private class ActuallyListener implements Property.ValueChangeListener {

        @Override
        public void valueChange(Property.ValueChangeEvent event) {
            try {
                float  actuallyPaid = Float.valueOf((String)event.getProperty().getValue());
                tfGaveChange.setValue(actuallyPaid - pricePaid);
                btApply.setEnabled(true);
            } catch (NumberFormatException e) {
                showWarningGMsg("实付格式错误!");
            }

        }
    }

    private void showWarningGMsg(String msg) {
        this.getWindow().showNotification("", msg, Window.Notification.TYPE_WARNING_MESSAGE);
    }
}