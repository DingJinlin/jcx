package com.djl.jcx.ui.table;

/**
 * User: Administrator
 * Date: 13-3-22
 * Time: 上午9:26
 */

import com.djl.jcx.data.dao.IFactoryDao;
import com.djl.jcx.data.model.FactoryModel;
import com.djl.jcx.ui.table.container.FactoryContainerGetter;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

public class FactoryTableLayout extends AbstractTableLayout<FactoryModel, IFactoryDao> {
    public FactoryTableLayout() {
        super(new Table(), new FactoryContainerGetter());
        BeanItemContainer<FactoryModel> container = containerGetter.getContainer();

        initTable(getTable(), container);
        addComponent(getTable());
    }

    private void initTable(Table table, BeanItemContainer<FactoryModel> container) {
        table.setContainerDataSource(container);
        table.setSelectable(true);

        table.setPageLength(20);
        table.setWidth("100%");
        table.setHeight("100%");

        // Set headers
        table.setVisibleColumns(containerGetter.getColumns());
        table.setColumnHeaders(containerGetter.getColumnsHeaders());

        // Set alignments
        table.setColumnAlignments(new String[]{Table.ALIGN_LEFT,
                Table.ALIGN_RIGHT, Table.ALIGN_RIGHT, Table.ALIGN_RIGHT});

        // Set column widths
        table.setColumnExpandRatio(FactoryContainerGetter.H_NAME, 1);

        // Enable footer
        table.setFooterVisible(true);
    }
}
