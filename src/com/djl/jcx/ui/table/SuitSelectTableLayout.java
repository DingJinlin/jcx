package com.djl.jcx.ui.table;

/**
 * User: Administrator
 * Date: 13-3-22
 * Time: 上午9:26
 */

import com.djl.jcx.data.dao.ISuitDao;
import com.djl.jcx.data.model.DepositoryModel;
import com.djl.jcx.data.model.SuitModel;
import com.djl.jcx.ui.layout.ISelectModelInfo;
import com.djl.jcx.ui.table.container.SuitContainerGetter;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.*;

public class SuitSelectTableLayout extends AbstractTableLayout<SuitModel, ISuitDao> {
    public static final String S_CAPTION = "选择商品:";
    private static final String S_SEARCH_CAPTION = "输入商品名缩写查询:";

    TextField tfShortName;

    public SuitSelectTableLayout(ISelectModelInfo selectInfo) {
        super(new Table(), new SuitContainerGetter());
        this.selectInfo = selectInfo;

        BeanItemContainer<SuitModel> container = this.containerGetter.getContainer();
        addComponent(initInputZone());
        initTable(getTable(),  container);
    }

    private void initTable(Table table, BeanItemContainer<SuitModel> container) {
        table.setContainerDataSource(container);
        table.setSelectable(true);

        table.setPageLength(20);
        table.setWidth("100%");
        table.setHeight("100%");

        // Set headers
        table.setVisibleColumns(containerGetter.getColumns());
        table.setColumnHeaders(containerGetter.getColumnsHeaders());

        // Set alignments
        table.setColumnAlignments(new String[]{Table.ALIGN_LEFT, Table.ALIGN_LEFT, Table.ALIGN_RIGHT, Table.ALIGN_RIGHT,
                Table.ALIGN_RIGHT, Table.ALIGN_RIGHT, Table.ALIGN_RIGHT, Table.ALIGN_RIGHT});

        // Set column widths
        table.setColumnExpandRatio(SuitContainerGetter.H_NAME, 1);

        addComponent(getTable());
        getTable().addListener(new ItemSelect());
    }

    /**
     * 初始化输入区
     * @return layout
     */
    private Layout initInputZone() {
        HorizontalLayout lytShortName = new HorizontalLayout();
        lytShortName.setMargin(true);
        lytShortName.setSpacing(true);

        tfShortName = new TextField(S_SEARCH_CAPTION);
        tfShortName.setImmediate(true);
        tfShortName.addListener(new ShortNameInput());
        lytShortName.addComponent(tfShortName);

        return lytShortName;
    }

    ISelectModelInfo selectInfo;

    public String getTitle() {
        return S_CAPTION;
    }

    /**
     * 输入缩写事件处理
     */
    private class ShortNameInput implements FieldEvents.TextChangeListener {
        @Override
        public void textChange(FieldEvents.TextChangeEvent event) {
            String name = event.getText();
            containerGetter.fillContainerByName(name);
        }
    }

    /**
     * 表格选择事件处理
     */
    private class ItemSelect implements ItemClickEvent.ItemClickListener {
        @Override
        public void itemClick(ItemClickEvent event) {
            if(event.isDoubleClick()) {
                Item item = event.getItem();
                SuitModel model = ((BeanItem<SuitModel>)event.getItem()).getBean();
                selectInfo.setSelectSuit(model);

                Window subWindow = getWindow();
                subWindow.getParent().removeWindow(subWindow);
            }
        }
    }
}

