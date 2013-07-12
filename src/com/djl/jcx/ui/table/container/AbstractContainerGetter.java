package com.djl.jcx.ui.table.container;

import com.djl.jcx.data.dao.IBaseDao;
import com.djl.jcx.data.model.BaseModel;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * User: Administrator
 * Date: 13-3-27
 * Time: 下午2:10
 */
public abstract class AbstractContainerGetter<M extends BaseModel, D extends IBaseDao<M>> implements Serializable {
    final protected Set<M> containerCollection;
    final protected BeanItemContainer<M> container;
    final private D dao;

    protected AbstractContainerGetter(D dao, Class<M> type) {
        containerCollection = new HashSet<M>();
        container = new BeanItemContainer(type);
        this.dao = dao;
    }

    /**
     * 初始化表格数据
     */
    public void initFillContainer() {
        Collection<M> models = dao.listAll();
        containerCollection.addAll(models);
        fillContainer(models);
    }

    /**
     * 刷新表格数据
     */
    public void refreshContainer() {
        containerCollection.clear();
        container.removeAllItems();
        initFillContainer();
    }

    protected void fillContainerByAll() {
        container.removeAllItems();
        fillContainer(containerCollection);
    }

    /**
     * 在container中增加一条记录
     * @param model
     */
    public void addItem(M model) {
        container.addItem(model);
        containerCollection.add(model);
    }

    /**
     * 在container中增加一条记录,并持久化
     * @param model
     */
    public void insertItem(M model) {
        Integer id = dao.save(model);
        model.setId(id);
        addItem(model);
    }

    public void insertAllItem() {
        for(M model : containerCollection) {
            dao.save(model);
        }
    }

    /**
     * 在container中移除一条记录
     * @param model
     */
    public void removeItem(M model) {
        container.removeItem(model);
        containerCollection.remove(model);
    }

    /**
     * 在container中移除一条记录,并持久化
     * @param model
     */
    public void delItem(M model) throws Exception {
        removeItem(model);
        dao.delete(model.getId());
    }

    /**
     * 在container中修改一条记录
     * @param model
     */
    public void modifyItem(M model) {
        M containerItem = getItem(model);
        containerItem.copyValue(model);
        container.addItem(containerItem);
        containerCollection.add(containerItem);
    }

    /**
     * 在container中修改一条记录,并持久化
     * @param model
     */
    public void updateItem(M model) {
        dao.update(model);
        modifyItem(model);
    }

    /**
     * 查询获取记录
     * @param model
     * @return
     */
    public M getItem(M model) {
        for(M item : containerCollection){
            if(item.getId().equals(model.getId())) {
                return item;
            }
        }

        return null;
    }

    /**
     * 清除容量中的数据
     */
    public void clearContainer() {
        container.removeAllItems();

    }

    /**
     * 清除所有数据包含缓存中的数据
     */
    public void clearAll() {
        container.removeAllItems();
        containerCollection.clear();
    }

    public void fillContainer(Collection<M> models) {
        container.addAll(models);
    }

    public BeanItemContainer<M> getContainer() {
        return container;
    }

    public abstract Object[] getColumns();
    public abstract String[] getColumnsHeaders();

    public String[] getColumnAlignments() {
        String[] alignments = new String[getColumnsHeaders().length];
        for(String alignment : alignments) {
            alignment = Table.ALIGN_LEFT;
        }

        return alignments;
    }
    public abstract M searchItem(M model);
    public abstract void fillContainerByName(String name);
}
