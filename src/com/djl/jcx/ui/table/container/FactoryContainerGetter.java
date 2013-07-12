package com.djl.jcx.ui.table.container;

import com.djl.jcx.data.dao.IFactoryDao;
import com.djl.jcx.data.model.FactoryModel;
import com.spring.SpringContextHelperHandler;

import java.util.Iterator;

/**
 * User: Administrator
 * Date: 13-3-22
 * Time: 上午9:30
 */
public class FactoryContainerGetter extends AbstractContainerGetter<FactoryModel, IFactoryDao> {
    // 字段名
    public static final String H_NAME = "name";
    public static final String H_ID = "id";
    public static final String H_ADDRESS = "address";
    public static final String H_CONTACT = "contactName";
    public static final String H_PHONE = "contactPhone";
    public static final String H_ABBREVIATION = "abbreviation";

    // 显示名
    public static final String V_NAME = "名字";
    public static final String V_ADDRESS = "Address";
    public static final String V_CONTACT = "Contact";
    public static final String V_PHONE = "Phone";
    public static final String V_ABBREVIATION = "abbreviation";

    static IFactoryDao factoryDao;

    static {
        factoryDao = SpringContextHelperHandler.getSch().getBean("factoryDao", IFactoryDao.class);
    }

    public FactoryContainerGetter() {
        super(factoryDao, FactoryModel.class);
        initFillContainer();
    }

    @Override
    public Object[] getColumns() {
        return new Object[]{H_NAME, H_ADDRESS, H_CONTACT, H_PHONE};
    }

    @Override
    public String[] getColumnsHeaders() {
        return new String[] {V_NAME, V_ADDRESS, V_CONTACT, V_PHONE};
    }

    @Override
    @Deprecated
    public FactoryModel searchItem(FactoryModel model) {
        return null;
    }

    @Override
    public void fillContainerByName(String name) {
        Iterator<FactoryModel> iterator = containerCollection.iterator();
        clearContainer();
        if(name != null && !name.isEmpty()) {
            while(iterator.hasNext()) {
                FactoryModel model = iterator.next();
                if(model.getAbbreviation().contains(name.toUpperCase())) {
                    addItem(model);
                }
            }
        } else {
            fillContainerByAll();
        }
    }
}
