package com.djl.jcx.ui.table.container;

import com.vaadin.data.util.AbstractContainer;

/**
 * User: Ding
 * Date: 13-3-21
 * Time: 下午10:39
 */
public interface IContainerGetter<M> {
    public AbstractContainer getContainer();
    public String[] getHeaders();

    /**
     * 在container中增加一条记录
     * @param model
     */
    public void addItem(M model);

    /**
     * 在container中增加一条记录,并持久化
     * @param model
     */
    public void insertItem(M model);

    /**
     * 在container中移除一条记录
     * @param model
     */
    public void removeItem(M model);

    /**
     * 在container中移除一条记录,并持久化
     * @param model
     */
    public void delItem(M model);

    /**
     * 在container中修改一条记录
     * @param model
     */
    public void modifyItem(M model);

    /**
     * 在container中修改一条记录,并持久化
     * @param model
     */
    public void updateItem(M model);
}
