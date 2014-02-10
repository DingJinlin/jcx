package com.djl.jcx.data.dao.ibatis;

import com.djl.jcx.data.dao.IFactoryDao;
import com.djl.jcx.data.model.FactoryModel;

import java.util.List;

/**
 * User: Administrator
 * Date: 13-2-19
 * Time: 下午3:17
 */
public class FactoryDaoImpl extends BaseDaoImpl<FactoryModel> implements IFactoryDao {
    public FactoryDaoImpl() {
        super("FactorySQL");
    }

    @Override
    public List<FactoryModel> queryByName(String name) {
        FactoryModel factory = new FactoryModel();
        factory.setName(name);
        return listByModel(factory);
    }

    @Override
    public List<FactoryModel> queryByContact(String contactName) {
        FactoryModel factory = new FactoryModel();
        factory.setContactName(contactName);
        return listByModel(factory);
    }
}
