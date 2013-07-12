package com.djl.jcx.ui.layout.form;

import com.djl.jcx.data.dao.IFactoryDao;
import com.djl.jcx.ui.table.AbstractTableLayout;
import com.djl.jcx.ui.table.container.FactoryContainerGetter;
import com.djl.util.*;
import com.djl.jcx.data.model.FactoryModel;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;

import java.util.Arrays;
import java.util.Set;

/**
 * User: Administrator
 * Date: 13-3-13
 * Time: 下午3:42
 */
public class FactoryInfoManage extends AManageWindow{
    // 参数常量定义
    private static final String COMMON_FIELD_WIDTH = "12em";

    // 字符串常量定义
    public static final String S_CAPTION_INSERT = "新增供应商";
    public static final String S_CAPTION_UPDATE = "修改供应商";

    public static final String ERR_MSG_NAME = "Please enter a factory Name";

    AbstractTableLayout<FactoryModel, IFactoryDao> tableLayout;

    private Form form;
    Button discardChanges;
    Button apply;

    public FactoryInfoManage(AbstractTableLayout<FactoryModel, IFactoryDao> tableLayout){
        this(tableLayout, null);
    }
    public FactoryInfoManage(AbstractTableLayout<FactoryModel, IFactoryDao> tableLayout, FactoryModel model){
        super();
        this.tableLayout = tableLayout;

        if(model == null) {
            model = new FactoryModel();
            caption = S_CAPTION_INSERT;
        } else {
            caption = S_CAPTION_UPDATE;
        }

        init(model);
    }

    private void init(final FactoryModel model) {
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
                    Integer id = model.getId();
                    String abbreviation = new GB2Alpha().String2Alpha(model.getName());
                    model.setAbbreviation(abbreviation);
                    if(id != null) {
                        tableLayout.getContainerGetter().updateItem(model);
                        Window subWindow = getWindow();
                        (subWindow.getParent()).removeWindow(subWindow);
                    } else {
                        tableLayout.getContainerGetter().insertItem(model);
                        removeComponent(form);
                        init(new FactoryModel());
                    }

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

    private void initFrom(FactoryModel model) {
        BeanItem<FactoryModel> item =new BeanItem<FactoryModel>(model); // item from
        form.setItemDataSource(item);

        form.setFormFieldFactory(new FieldFactory());
        form.setVisibleItemProperties(Arrays.asList(new String[]{
                FactoryContainerGetter.H_ID,
                FactoryContainerGetter.H_NAME,
                FactoryContainerGetter.H_ADDRESS,
                FactoryContainerGetter.H_CONTACT,
                FactoryContainerGetter.H_PHONE
        }));

        form.getItemProperty(FactoryContainerGetter.H_ID).setValue(model.getId());
        form.getItemProperty(FactoryContainerGetter.H_NAME).setValue(model.getName());
        form.getItemProperty(FactoryContainerGetter.H_ADDRESS).setValue(model.getAddress());
        form.getItemProperty(FactoryContainerGetter.H_CONTACT).setValue(model.getContactName());
        form.getItemProperty(FactoryContainerGetter.H_PHONE).setValue(model.getContactName());
    }

    private class FieldFactory extends DefaultFieldFactory {
        @Override
        public Field createField(Item item, Object propertyId, Component uiContext) {
            Field f = super.createField(item, propertyId, uiContext);
            f.setWidth("100%");

            if (FactoryContainerGetter.H_ID.equals(propertyId)) {
                TextField tf = (TextField) f;
                tf.setNullRepresentation("-1");
                tf.setVisible(false);
            } else if (FactoryContainerGetter.H_NAME.equals(propertyId)) {
                TextField tf = (TextField) f;
                tf.setCaption(FactoryContainerGetter.V_NAME);
                tf.setNullRepresentation("");
                tf.setRequired(true);
                tf.setRequiredError(ERR_MSG_NAME);
            } else if (FactoryContainerGetter.H_ADDRESS.equals(propertyId)) {
                TextField tf = (TextField) f;
                tf.setCaption(FactoryContainerGetter.V_ADDRESS);
                tf.setNullRepresentation("");
            } else if (FactoryContainerGetter.H_CONTACT.equals(propertyId)) {
                TextField tf = (TextField)f;
                tf.setCaption(FactoryContainerGetter.V_CONTACT);
                tf.setNullRepresentation("");
            } else if (FactoryContainerGetter.H_PHONE.equals(propertyId)) {
                TextField tf = (TextField)f;
                tf.setCaption(FactoryContainerGetter.V_PHONE);
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