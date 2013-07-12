package com.djl.jcx.ui.table;

/**
 * User: Administrator
 * Date: 13-3-22
 * Time: 上午9:26
 */

import com.djl.jcx.data.dao.IAccessDao;
import com.djl.jcx.data.dao.IDepositoryDao;
import com.djl.jcx.data.model.AccessModel;
import com.djl.jcx.data.model.AccessModel;
import com.djl.jcx.ui.table.container.AccessContainerGetter;
import com.djl.jcx.ui.table.container.DepositoryAddContainerGetter;
import com.djl.jcx.ui.table.container.DepositoryContainerGetter;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

public class AccessTableLayout extends AbstractTableLayout<AccessModel, IAccessDao> {
    public AccessTableLayout() {
        super(new Table(), new AccessContainerGetter());
        BeanItemContainer<AccessModel> container = this.containerGetter.getContainer();

        initTable(getTable(),  container);
        addComponent(getTable());
    }

    private void initTable(Table table, BeanItemContainer<AccessModel> container) {
        table.setContainerDataSource(container);
        table.setSelectable(true);

        table.setPageLength(20);
        table.setWidth("100%");
        table.setHeight("100%");

        // Set headers
        table.setVisibleColumns(containerGetter.getColumns());
        table.setColumnHeaders(containerGetter.getColumnsHeaders());
        table.setColumnAlignments(containerGetter.getColumnAlignments());

        // Set column widths
        table.setColumnExpandRatio(AccessContainerGetter.H_SUIT_NAME, 1);

        // Enable footer
        table.setFooterVisible(true);
    }
}
