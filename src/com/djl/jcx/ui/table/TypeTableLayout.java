package com.djl.jcx.ui.table;

/**
 * User: Administrator
 * Date: 13-3-22
 * Time: 上午9:26
 */

import com.djl.jcx.data.dao.ITypeDao;
import com.djl.jcx.data.model.TypeModel;
import com.djl.jcx.ui.table.container.TypeContainerGetter;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

public class TypeTableLayout extends AbstractTableLayout<TypeModel, ITypeDao> {
    public TypeTableLayout() {
        super(new Table(), new TypeContainerGetter());
        BeanItemContainer<TypeModel> container = containerGetter.getContainer();

        initTable(getTable(), container);
        addComponent(getTable());
    }

    private void initTable(Table table, BeanItemContainer<TypeModel> container) {
        table.setContainerDataSource(container);
        table.setSelectable(true);

        table.setPageLength(20);
        table.setWidth("100%");
        table.setHeight("100%");

        // Set headers
        table.setVisibleColumns(containerGetter.getColumns());
        table.setColumnHeaders(containerGetter.getColumnsHeaders());

        // Set alignments
        table.setColumnAlignments(new String[]{Table.ALIGN_LEFT});

        // Set column widths
        table.setColumnExpandRatio(TypeContainerGetter.H_NAME, 1);

        // Enable footer
        table.setFooterVisible(true);
    }
}
