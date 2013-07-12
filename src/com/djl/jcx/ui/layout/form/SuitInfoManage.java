package com.djl.jcx.ui.layout.form;

import com.djl.jcx.data.dao.IFactoryDao;
import com.djl.jcx.data.dao.ITypeDao;
import com.djl.jcx.data.model.FactoryModel;
import com.djl.jcx.data.model.SuitModel;
import com.djl.jcx.data.model.TypeModel;
import com.djl.jcx.ui.table.SuitTableLayout;
import com.djl.jcx.ui.table.container.AbstractContainerGetter;
import com.djl.jcx.ui.table.container.FactoryContainerGetter;
import com.djl.jcx.ui.table.container.SuitContainerGetter;
import com.djl.jcx.ui.table.container.TypeContainerGetter;
import com.djl.util.GB2Alpha;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;

import java.util.Arrays;
import java.util.Date;

/**
 * User: Administrator
 * Date: 13-3-13
 * Time: 下午3:42
 */
public class SuitInfoManage extends AManageWindow {
    // 参数常量定义
    private static final String COMMON_FIELD_WIDTH = "12em";

    // 字符串常量定义
    public static final String S_CAPTION_INSERT = "新增商品";
    public static final String S_CAPTION_UPDATE = "修改商品";

    public static final String ERR_MSG_NAME = "未输入商品名";
    public static final String ERR_MSG_SELLING_PRICE = "未输入售价";

    SuitTableLayout tableLayout;
    AbstractContainerGetter<FactoryModel, IFactoryDao> factoryContainerGetter;
    AbstractContainerGetter<TypeModel, ITypeDao> typeContainerGetter;

    private Form form;
    Button discardChanges;
    Button apply;

    public SuitInfoManage(SuitTableLayout tableLayout) {
        this(tableLayout, null);
    }

    public SuitInfoManage(SuitTableLayout tableLayout, SuitModel model) {
        super();
        this.tableLayout = tableLayout;
        factoryContainerGetter = new FactoryContainerGetter();
        typeContainerGetter = new TypeContainerGetter();

        if(model == null) {
            model = new SuitModel();
            caption = S_CAPTION_INSERT;
        } else {
            caption = S_CAPTION_UPDATE;
        }

        init(model);
    }

    private void init(final SuitModel model) {
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

                    String code =  model.getInternationalCode();
                    if(code == null || code.isEmpty() || code.length() < 12) {
                        SuitContainerGetter containerGetter = (SuitContainerGetter)tableLayout.getContainerGetter();
                        SuitModel suit = containerGetter.searchMaxIdSuit();
                        if(suit == null) {
                            code = String.valueOf(new Date().getTime()).substring(0, 12);
                        } else {
                            code = String.valueOf(Long.valueOf(suit.getInternationalCode().substring(0, 12)) + 1);
                        }
                    } else {
                        code = model.getInternationalCode().substring(0, 12);
                    }
                    code = calculateInternationalCodeChecksum(code);
                    model.setInternationalCode(code);

                    if(id != null) {
                        tableLayout.getContainerGetter().updateItem(model);
                        Window subWindow = getWindow();
                        (subWindow.getParent()).removeWindow(subWindow);
                    } else {
                        tableLayout.getContainerGetter().insertItem(model);
                        removeComponent(form);
                        init(new SuitModel());
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

    private void initFrom(SuitModel model) {
        BeanItem<SuitModel> item = new BeanItem<SuitModel>(model); // item from
        form.setItemDataSource(item);

        form.setFormFieldFactory(new FieldFactory());
        form.setVisibleItemProperties(Arrays.asList(new String[]{
                SuitContainerGetter.H_ID,
                SuitContainerGetter.H_NAME,
                SuitContainerGetter.H_INTERNATIONAL_CODE,
                SuitContainerGetter.H_TYPE,
                SuitContainerGetter.H_FACTORY,
                SuitContainerGetter.H_STANDARD,
                SuitContainerGetter.H_UNIT,
                SuitContainerGetter.H_PURCHASE_PRICE,
                SuitContainerGetter.H_SELLING_PRICE,
                SuitContainerGetter.H_COMMENT
        }));

        form.getItemProperty(SuitContainerGetter.H_ID).setValue(model.getId());
        form.getItemProperty(SuitContainerGetter.H_NAME).setValue(model.getName());
        form.getItemProperty(SuitContainerGetter.H_INTERNATIONAL_CODE).setValue(model.getInternationalCode());
        form.getItemProperty(SuitContainerGetter.H_TYPE).setValue(model.getType());
        form.getItemProperty(SuitContainerGetter.H_FACTORY).setValue(model.getFactory());
        form.getItemProperty(SuitContainerGetter.H_STANDARD).setValue(model.getStandard());
        form.getItemProperty(SuitContainerGetter.H_UNIT).setValue(model.getUnit());
        form.getItemProperty(SuitContainerGetter.H_PURCHASE_PRICE).setValue(model.getPurchasePrice());
        form.getItemProperty(SuitContainerGetter.H_SELLING_PRICE).setValue(model.getSellingPrice());
        form.getItemProperty(SuitContainerGetter.H_COMMENT).setValue(model.getComment());
    }

    private class FieldFactory extends DefaultFieldFactory {
        @Override
        public Field createField(Item item, Object propertyId, Component uiContext) {
            Field f = super.createField(item, propertyId, uiContext);
            f.setWidth("100%");

            if (SuitContainerGetter.H_ID.equals(propertyId)) {
                TextField tf = (TextField) f;
                tf.setNullRepresentation("-1");
                tf.setVisible(false);
            } else if (SuitContainerGetter.H_NAME.equals(propertyId)) {
                TextField tf = (TextField) f;
                tf.setCaption(SuitContainerGetter.V_NAME);
                tf.setNullRepresentation("");
                tf.setRequired(true);
                tf.setRequiredError(ERR_MSG_NAME);
               
            } else if (SuitContainerGetter.H_INTERNATIONAL_CODE.equals(propertyId)) {
                TextField tf = (TextField) f;
                tf.setCaption(SuitContainerGetter.V_INTERNATIONAL_CODE);
                tf.setNullRepresentation("");
               
            } else if (SuitContainerGetter.H_TYPE.equals(propertyId)) {
                return createComboBox(SuitContainerGetter.V_TYPE, typeContainerGetter.getContainer());
            } else if (SuitContainerGetter.H_FACTORY.equals(propertyId)) {
                return createComboBox(SuitContainerGetter.V_FACTORY, factoryContainerGetter.getContainer());
            } else if (SuitContainerGetter.H_STANDARD.equals(propertyId)) {
                TextField tf = (TextField)f;
                tf.setCaption(SuitContainerGetter.V_STANDARD);
                tf.setNullRepresentation("");
                tf.setNullSettingAllowed(true);
               
            } else if (SuitContainerGetter.H_UNIT.equals(propertyId)) {
                TextField tf = (TextField)f;
                tf.setCaption(SuitContainerGetter.V_UNIT);
                tf.setNullRepresentation("");
                tf.setNullSettingAllowed(true);
               
            } else if (SuitContainerGetter.H_PURCHASE_PRICE.equals(propertyId)) {
                TextField tf = (TextField)f;
                tf.setCaption(SuitContainerGetter.V_PURCHASE_PRICE);
                tf.setNullRepresentation("");
                tf.setNullSettingAllowed(true);
               
            } else if (SuitContainerGetter.H_SELLING_PRICE.equals(propertyId)) {
                TextField tf = (TextField)f;
                tf.setCaption(SuitContainerGetter.V_SELLING_PRICE);
                tf.setNullRepresentation("");
                tf.setRequired(true);
                tf.setRequiredError(ERR_MSG_NAME);
               
            } else if (SuitContainerGetter.H_COMMENT.equals(propertyId)) {
                TextField tf = (TextField)f;
                tf.setCaption(SuitContainerGetter.V_COMMENT);
                tf.setNullRepresentation("");
                tf.setNullSettingAllowed(true);
               
            }

            return f;
        }

        private ComboBox createComboBox(String caption, Container dataSource) {
            ComboBox cb = new ComboBox(caption, dataSource);
            cb.setWidth("100%");

            cb.setItemCaptionPropertyId(FactoryContainerGetter.H_NAME);
            cb.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_ID);
            cb.setImmediate(true);
            cb.setNullSelectionAllowed(false);
            cb.setFilteringMode(AbstractSelect.Filtering.FILTERINGMODE_CONTAINS);
            cb.setWidth("400px");
            cb.setWidth(COMMON_FIELD_WIDTH);

            return cb;
        }
    }

    String caption;
    @Override
    public String getTitle() {
        return caption;
    }

    /**
     * 计算EAN13条码的校验码
     * N1 N2 N3 N4 N5 N6 N7 N8 N9 N10 N11 N12 C
     * 检查码之计算步骤如下：
     * C1 = N1+ N3+N5+N7+N9+N11C2 = (N2+N4+N6+N8+N10+N12)× 3
     * CC = (C1+C2)　取个位数
     * C (检查码) = 10 - CC　(若值为10，则取0)
     * @param value
     * @return 返回EAN13条码
     */
    public static String calculateInternationalCodeChecksum(String value) {
        int c1 = 0;
        int c2 = 0;
        for(int i = 0; i != 12; i++) {
            if(i % 2 == 0) {
                c1 += Integer.valueOf(value.substring(i, i + 1));
            } else {
                c2 += Integer.valueOf(value.substring(i, i + 1));
            }
        }

        int cc = (c1 + (c2 * 3)) % 10;
        int c = (10 - cc) % 10;
        return value + String.valueOf(c);
    }
}