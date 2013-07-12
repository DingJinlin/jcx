package com.djl.jcx.ui.layout.form;

import com.djl.jcx.data.dao.IDepositoryDao;
import com.djl.jcx.data.dao.IFactoryDao;
import com.djl.jcx.data.dao.ITypeDao;
import com.djl.jcx.data.model.DepositoryModel;
import com.djl.jcx.data.model.FactoryModel;
import com.djl.jcx.data.model.TypeModel;
import com.djl.jcx.ui.table.AbstractTableLayout;
import com.djl.jcx.ui.table.container.AbstractContainerGetter;
import com.djl.jcx.ui.table.container.DepositoryContainerGetter;
import com.djl.jcx.ui.table.container.FactoryContainerGetter;
import com.djl.jcx.ui.table.container.TypeContainerGetter;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;

import java.util.Arrays;

/**
 * User: Administrator
 * Date: 13-3-13
 * Time: 下午3:42
 */
public class DepositoryInfoManage extends AManageWindow {
    // 参数常量定义
    private static final String COMMON_FIELD_WIDTH = "12em";

    // 字符串常量定义
    public static final String S_CAPTION_UPDATE = "提交";

    AbstractTableLayout<DepositoryModel, IDepositoryDao> tableLayout;
    AbstractContainerGetter<FactoryModel, IFactoryDao> factoryContainerGetter;
    AbstractContainerGetter<TypeModel, ITypeDao> typeContainerGetter;

    private Form form;
    Button discardChanges;
    Button apply;

    public DepositoryInfoManage(AbstractTableLayout<DepositoryModel, IDepositoryDao> tableLayout) {
        this(tableLayout, null);
    }
    public DepositoryInfoManage(AbstractTableLayout<DepositoryModel, IDepositoryDao> tableLayout, DepositoryModel model) {
        super();
        this.tableLayout = tableLayout;
        factoryContainerGetter = new FactoryContainerGetter();
        typeContainerGetter = new TypeContainerGetter();
        caption = S_CAPTION_UPDATE;
        init(model);
    }

    private void init(final DepositoryModel model) {
        form = new Form();
        form.setWriteThrough(false);
        form.setInvalidCommitted(false);
        initFrom(model);
        addComponent(form);

        // The cancel / btApply buttons
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        discardChanges = new Button(BUT_DISCARD,
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        form.discard();
                    }
                });

        apply = new Button(BUT_APPLY, new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    form.commit();
                    DepositoryModel resultModel = tableLayout.getContainerGetter().searchItem(model);
                    int amount = resultModel.getAmount() + model.getAmount();
                    resultModel.setAmount(amount);
                    tableLayout.getContainerGetter().updateItem(resultModel);
                    Window subWindow = getWindow();
                    (subWindow.getParent()).removeWindow(subWindow);

                    tableLayout.getTable().requestRepaint();
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        });
        buttons.addComponent(discardChanges);
        buttons.setComponentAlignment(discardChanges, Alignment.MIDDLE_LEFT);
        buttons.addComponent(apply);

        form.getFooter().addComponent(buttons);
        form.getFooter().setMargin(false, false, true, true);
    }

    private void initFrom(DepositoryModel model) {
        BeanItem<DepositoryModel> item = new BeanItem<DepositoryModel>(model); // item from
        form.setItemDataSource(item);

        form.setFormFieldFactory(new FieldFactory());
        form.setVisibleItemProperties(Arrays.asList(new String[]{
                DepositoryContainerGetter.H_ID,
                DepositoryContainerGetter.H_NAME,
                DepositoryContainerGetter.H_INTERNATIONAL_CODE,
                DepositoryContainerGetter.H_TYPE,
                DepositoryContainerGetter.H_FACTORY,
                DepositoryContainerGetter.H_STANDARD,
                DepositoryContainerGetter.H_UNIT,
                DepositoryContainerGetter.H_AMOUNT
        }));

        form.getItemProperty(DepositoryContainerGetter.H_ID).setValue(model.getId());
        form.getItemProperty(DepositoryContainerGetter.H_NAME).setValue(model.getSuit().getName());
        form.getItemProperty(DepositoryContainerGetter.H_INTERNATIONAL_CODE).setValue(model.getSuit().getInternationalCode());
        form.getItemProperty(DepositoryContainerGetter.H_TYPE).setValue(model.getSuit().getType());
        form.getItemProperty(DepositoryContainerGetter.H_FACTORY).setValue(model.getSuit().getFactory());
        form.getItemProperty(DepositoryContainerGetter.H_STANDARD).setValue(model.getSuit().getStandard());
        form.getItemProperty(DepositoryContainerGetter.H_UNIT).setValue(model.getSuit().getUnit());
        form.getItemProperty(DepositoryContainerGetter.H_AMOUNT).setValue(model.getAmount());
        form.getItemProperty(DepositoryContainerGetter.H_COMMENT).setValue(model.getSuit().getComment());
    }

    private class FieldFactory extends DefaultFieldFactory {
        @Override
        public Field createField(Item item, Object propertyId, Component uiContext) {
            Field f = super.createField(item, propertyId, uiContext);
            f.setWidth("100%");

            if (DepositoryContainerGetter.H_ID.equals(propertyId)) {
                TextField tf = (TextField) f;
                tf.setNullRepresentation("-1");
                tf.setVisible(false);
            } else if (DepositoryContainerGetter.H_NAME.equals(propertyId)) {
                TextField tf = (TextField) f;
                tf.setCaption(DepositoryContainerGetter.V_NAME);
                tf.setEnabled(false);
            } else if (DepositoryContainerGetter.H_INTERNATIONAL_CODE.equals(propertyId)) {
                TextField tf = (TextField) f;
                tf.setCaption(DepositoryContainerGetter.V_INTERNATIONAL_CODE);
                tf.setEnabled(false);
            } else if (DepositoryContainerGetter.H_TYPE.equals(propertyId)) {
                TextField tf = (TextField) f;
                tf.setCaption(DepositoryContainerGetter.V_TYPE);
                tf.setEnabled(false);
            } else if (DepositoryContainerGetter.H_FACTORY.equals(propertyId)) {
                TextField tf = (TextField) f;
                tf.setCaption(DepositoryContainerGetter.V_FACTORY);
                tf.setEnabled(false);
            } else if (DepositoryContainerGetter.H_STANDARD.equals(propertyId)) {
                TextField tf = (TextField)f;
                tf.setCaption(DepositoryContainerGetter.V_STANDARD);
                tf.setEnabled(false);
            } else if (DepositoryContainerGetter.H_UNIT.equals(propertyId)) {
                TextField tf = (TextField)f;
                tf.setCaption(DepositoryContainerGetter.V_UNIT);
                tf.setEnabled(false);
            } else if (DepositoryContainerGetter.H_AMOUNT.equals(propertyId)) {
                TextField tf = (TextField)f;
                tf.setCaption(DepositoryContainerGetter.V_AMOUNT);
                tf.setNullRepresentation("0");
                tf.setNullSettingAllowed(true);
            } else if (DepositoryContainerGetter.H_COMMENT.equals(propertyId)) {
                TextField tf = (TextField)f;
                tf.setCaption(DepositoryContainerGetter.V_COMMENT);
                tf.setNullRepresentation("");
                tf.setNullSettingAllowed(true);
            }

            return f;
        }
    }

    String caption;
    @Override
    public String getTitle() {
        return caption;
    }
}