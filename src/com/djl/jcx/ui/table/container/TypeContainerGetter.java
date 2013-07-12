package com.djl.jcx.ui.table.container;

import com.djl.jcx.data.dao.ITypeDao;
import com.djl.jcx.data.model.TypeModel;
import com.spring.SpringContextHelperHandler;

import java.util.Iterator;

/**
 * User: Administrator
 * Date: 13-3-22
 * Time: 上午9:30
 */
public class TypeContainerGetter extends AbstractContainerGetter<TypeModel, ITypeDao> {
    // 字段名
    public static final String H_ID = "id";
    public static final String H_NAME = "name";
    // 显示名
    public static final String V_NAME = "name";

    static ITypeDao dao;

    static {
        dao = SpringContextHelperHandler.getSch().getBean("typeDao", ITypeDao.class);
    }

    public TypeContainerGetter() {
        super(dao, TypeModel.class);
        initFillContainer();
    }

    @Override
    public Object[] getColumns() {
        return new Object[] {H_NAME};
    }
    @Override
    public String[] getColumnsHeaders() {
        return new String[]{V_NAME};
    }

    @Override
    @Deprecated
    public TypeModel searchItem(TypeModel model) {
        return null;
    }

    @Override
    public void fillContainerByName(String name) {
        Iterator<TypeModel> iterator = containerCollection.iterator();
        clearContainer();
        if(name != null && !name.isEmpty()) {
            while(iterator.hasNext()) {
                TypeModel model = iterator.next();
                if(model.getAbbreviation().contains(name.toUpperCase())) {
                    addItem(model);
                }
            }
        } else {
            fillContainerByAll();
        }
    }
}
