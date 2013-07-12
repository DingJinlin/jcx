package com.djl.jcx.ui.table;

/**
 * User: Administrator
 * Date: 13-3-22
 * Time: 上午9:26
 */

import com.djl.jcx.data.dao.ISuitDao;
import com.djl.jcx.data.model.SuitModel;
import com.djl.jcx.ui.table.container.SuitContainerGetter;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

public class SuitTableLayout extends AbstractTableLayout<SuitModel, ISuitDao> {
    public SuitTableLayout() {
        super(new Table(), new SuitContainerGetter());
        BeanItemContainer<SuitModel> container = this.containerGetter.getContainer();

        initTable(getTable(),  container);
        addComponent(getTable());
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
        table.setColumnAlignments(new String[]{Table.ALIGN_CENTER, Table.ALIGN_CENTER, Table.ALIGN_CENTER, Table.ALIGN_CENTER,
                Table.ALIGN_LEFT, Table.ALIGN_RIGHT, Table.ALIGN_RIGHT, Table.ALIGN_RIGHT});

        // Set column widths
        table.setColumnExpandRatio(SuitContainerGetter.H_INTERNATIONAL_CODE, 1);

        // Enable footer
        table.setFooterVisible(true);
    }
}
