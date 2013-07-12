package com.djl.jcx.ui.table;

/**
 * User: Administrator
 * Date: 13-3-22
 * Time: 上午9:26
 */

import com.djl.jcx.data.dao.ISellingDao;
import com.djl.jcx.data.model.SellingModel;
import com.djl.jcx.ui.table.container.DepositoryContainerGetter;
import com.djl.jcx.ui.table.container.SellingContainerGetter;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

public class SellingTableLayout extends AbstractTableLayout<SellingModel, ISellingDao> {
    public SellingTableLayout() {
        super(new Table(), new SellingContainerGetter());
        BeanItemContainer<SellingModel> container = this.containerGetter.getContainer();

        initTable(getTable(),  container);
        addComponent(getTable());
    }

    private void initTable(final Table table, BeanItemContainer<SellingModel> container) {
        table.setContainerDataSource(container);
        table.setSelectable(true);

        table.setPageLength(20);
        table.setWidth("100%");
        table.setHeight("100%");

        // Set headers
        table.setVisibleColumns(containerGetter.getColumns());
        table.setColumnHeaders(containerGetter.getColumnsHeaders());

        // Set alignments
        table.setColumnAlignments(new String[]{Table.ALIGN_LEFT, Table.ALIGN_RIGHT, Table.ALIGN_RIGHT, Table.ALIGN_RIGHT,
                Table.ALIGN_RIGHT, Table.ALIGN_RIGHT});

        // Set column widths
        table.setColumnExpandRatio(DepositoryContainerGetter.H_NAME, 1);

        // Enable footer
        table.setFooterVisible(true);

        table.setImmediate(true);
        table.setWriteThrough(false);
        table.setSelectable(true);
    }
}
