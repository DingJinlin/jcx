package com.djl.jcx.ui.table;

/**
 * User: Administrator
 * Date: 13-3-22
 * Time: 上午9:26
 */

import com.djl.jcx.data.dao.IBaseDao;
import com.djl.jcx.data.model.BaseModel;
import com.djl.jcx.ui.table.container.AbstractContainerGetter;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import java.util.HashSet;
import java.util.Set;

public class AbstractTableLayout<M extends BaseModel, D extends IBaseDao<M>> extends VerticalLayout {
    final Table table;
    final AbstractContainerGetter<M, D> containerGetter;

    public AbstractTableLayout(Table table, AbstractContainerGetter<M, D> containerGetter) {
        this.table = table;
        this.containerGetter = containerGetter;
    }

    /**
     * 获取选中的记录
     * @return 选中的记录
     */
    public Set<M> getSelectValue() {
        Set<M> values = new HashSet<M>();
        Object object = table.getValue();

        if(object == null) {
            return values;
        }

        if(object instanceof Set) {
            values.addAll((Set<M>) object);
        } else {
            values.add((M)object);
        }

        return values;
    }

    /**
     * 清除选中的记录
     */
    public void removeSelectValue() {
        Set<M> selected = getSelectValue();
        for(M c : selected) {
            containerGetter.removeItem(c);
        }
    }

    public AbstractContainerGetter<M, D> getContainerGetter() {
        return containerGetter;
    }

    public Table getTable() {
        return table;
    }
}
