package com.djl.jcx.ui.layout.form;

import com.djl.jcx.data.dao.ITypeDao;
import com.djl.jcx.data.model.SuitModel;
import com.djl.jcx.ui.table.AbstractTableLayout;
import com.djl.jcx.ui.table.container.FactoryContainerGetter;
import com.djl.jcx.ui.table.container.TypeContainerGetter;
import com.djl.util.*;
import com.djl.jcx.data.model.TypeModel;
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
public class TypeInfoManage extends AManageWindow {
    // 参数常量定义
    private static final String COMMON_FIELD_WIDTH = "12em";

    // 字符串常量定义
    public static final String S_CAPTION_INSERT = "新增商品类型";
    public static final String S_CAPTION_UPDATE = "修改商品类型";

    public static final String ERR_MSG_NAME = "Please enter a type Name";

    AbstractTableLayout<TypeModel, ITypeDao> tableLayout;

    private Form form;
    Button discardChanges;
    Button apply;

    public TypeInfoManage(AbstractTableLayout<TypeModel, ITypeDao> tableLayout) {
        this(tableLayout, null);
    }
    public TypeInfoManage(AbstractTableLayout<TypeModel, ITypeDao> tableLayout, TypeModel model) {
        super();
        this.tableLayout = tableLayout;

        if(model == null) {
            model = new TypeModel();
            caption = S_CAPTION_INSERT;
        } else {
            caption = S_CAPTION_UPDATE;
        }

        init(model);
    }

    private void init(final TypeModel model) {
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
                        init(new TypeModel());
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

    private void initFrom(TypeModel model) {
        BeanItem<TypeModel> item = new BeanItem<TypeModel>(model); // item from
        form.setItemDataSource(item);

        form.setFormFieldFactory(new FieldFactory());
        form.setVisibleItemProperties(Arrays.asList(new String[]{
                TypeContainerGetter.H_ID,
                TypeContainerGetter.H_NAME,
        }));

        form.getItemProperty(TypeContainerGetter.H_ID).setValue(model.getId());
        form.getItemProperty(TypeContainerGetter.H_NAME).setValue(model.getName());
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